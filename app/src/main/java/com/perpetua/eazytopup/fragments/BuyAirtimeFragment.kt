package com.perpetua.eazytopup.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log.d
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.perpetua.eazytopup.databinding.FragmentBuyAirtimeBinding
import java.lang.StringBuilder

import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.perpetua.eazytopup.HistoryItem
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.SharedPreferencesManager
import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import com.perpetua.eazytopup.utils.Resource
import com.perpetua.eazytopup.viewmodels.AirtimeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyAirtimeFragment : Fragment() {
    val airtimeViewModel by viewModel<AirtimeViewModel>()
    private val CONTACT_PICK_CODE: Int = 2
    private val TAG: String? = "BuyAirtimeFragment"
    private val REQUEST_PERMISSIONS_REQUEST_CODE: Int = 1
    private var _binding: FragmentBuyAirtimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var myPhoneNumber: String
    private lateinit var phoneNumberToTopup: String
    private lateinit var amount: String

    //private lateinit var binding: FragmentBuyAirtimeBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager


    val args: BuyAirtimeFragmentArgs by navArgs()
    val phoneNumberPattern =
        "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"
    val amountRegex = "^0+(?!\$)"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyAirtimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())




        val buyFor = args.buyFor
        setupUI(view)
        binding.phoneNumberTopupField.setEndIconOnClickListener {
            it.hideSoftKeyboard()
            if (!checkPermissions()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions()
                }
            }
            else {
                pickContact()

            }
        }

        val countryCode = "+254"
        if(buyFor.equals("others")){
            binding.btnBuyAirtime.setOnClickListener {
                myPhoneNumber = binding.myPhoneNumber.text.toString().trim()
                phoneNumberToTopup = binding.phoneNumberToTopup.text.toString().trim()
                amount =binding.amount.text.toString().trim()

                val validMyPhoneNumber = countryCode.plus(getDigitsOnlyContact(myPhoneNumber))
                val validPhoneToTopup = countryCode.plus(getDigitsOnlyContact(phoneNumberToTopup))



                if(!validateAmount(amount, binding.amountField) or
                    !validatePhoneNumber(validMyPhoneNumber, binding.phoneNumberField) or
                    !validatePhoneNumber(validPhoneToTopup, binding.phoneNumberTopupField) ){
                    return@setOnClickListener
                }

                val stringBuilder = StringBuilder(amount)
                while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
                    stringBuilder.deleteCharAt(0)
                }

                val airtimeNumber = normalizePhoneNumber(validMyPhoneNumber)
                val airtimeNumberToTopup = normalizePhoneNumber(validPhoneToTopup)
                val airtimeAmount = stringBuilder.toString()
                val airtimeForOther = AirtimeForOther(airtimeNumber, airtimeNumberToTopup, airtimeAmount)

                if (airtimeNumberToTopup.isNotBlank()) {
                    sharedPreferencesManager.saveHistoryItem(HistoryItem(airtimeNumberToTopup))
                    //binding.phoneNumberToTopup.text?.clear() // Clear the input field
                }


                val PointsAmount = binding.amount.text.toString().toDoubleOrNull() ?: 0.0
                val points = (PointsAmount * 0.04).toFloat()
                // Save points using SharedPreferencesManager
                sharedPreferencesManager.savePoints(points)
                sharedPreferencesManager.saveAmount(amount)



                showRationaleDialog(
                    "Confirm",
                    "Buy airtime for $airtimeNumberToTopup \n Amount: $airtimeAmount ",
                    "EDIT",
                    "Ok"){ dialog, which ->
                    makePurchaseForOther(airtimeForOther)
                    binding.myPhoneNumber.setText("")
                    binding.phoneNumberToTopup.setText("")
                    binding.amount.setText("")
                }

            }
        }
        if(buyFor.equals("self")){
            binding.buyForOthersLayout.visibility = View.GONE
            binding.btnBuyAirtime.setOnClickListener {
                myPhoneNumber = binding.myPhoneNumber.text.toString().trim()
                amount =binding.amount.text.toString().trim()

                val validPhoneNumber = (countryCode.plus(getDigitsOnlyContact(myPhoneNumber)))
                if(!validateAmount(amount, binding.amountField) or
                    !validatePhoneNumber(validPhoneNumber, binding.phoneNumberField) ){
                    return@setOnClickListener
                }


                val stringBuilder = StringBuilder(amount)
                while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
                    stringBuilder.deleteCharAt(0)
                }

                val airtimeNumber = (normalizePhoneNumber(validPhoneNumber))
                val airtimeAmount = stringBuilder.toString()

                val airtimeForSelf = AirtimeForSelf(airtimeNumber, airtimeAmount)

                showRationaleDialog(
                    "Confirm",
                    "Buy airtime for  $airtimeNumber \n Amount: $airtimeAmount ",
                    "EDIT",
                    "Ok"){ dialog, which ->
                    d(TAG, "Starting request")
                    makePurchaseForSelf(airtimeForSelf)
                    binding.myPhoneNumber.setText("")
                    binding.amount.setText("")

                }

            }
        }


    }
    fun makePurchaseForSelf(airtimeForSelf: AirtimeForSelf){
        airtimeViewModel.buyAirtimeForSelf(airtimeForSelf)
        airtimeViewModel.airtimeForSelfData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    d(TAG, "successful request: data ${it.data.toString()}")
                    Toast.makeText(requireContext(), "Successful: Follow Mpesa prompt to complete purchase", Toast.LENGTH_LONG).show()
                    parentFragment?.parentFragment?.findNavController()?.navigate(R.id.action_buyAirtimeFragment_to_homeHostFragment)
                }
                is Resource.Error -> {
                    d(TAG, "Unsuccessful request")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.PhoneNumberError ->{
                    d(TAG, "Unsuccessful request")
                    binding.phoneNumberField.error = it.message
                }
                is Resource.AmountError ->{
                    d(TAG, "Unsuccessful request")
                    binding.amountField.error = it.message
                }
                is Resource.Loading ->{

                    d(TAG, "loading request")
                }
            }
        })
    }
    fun makePurchaseForOther(airtimeForOther: AirtimeForOther){
        airtimeViewModel.buyAirtimeForOther(airtimeForOther)
        airtimeViewModel.airtimeForSelfData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    d(TAG, "successful request: data ${it.data.toString()}")
                    Toast.makeText(requireContext(), "Successful: Follow Mpesa prompt to complete purchase", Toast.LENGTH_LONG).show()
                    parentFragment?.parentFragment?.findNavController()?.navigate(R.id.action_buyAirtimeFragment_to_homeHostFragment)
                }
                is Resource.Error -> {

                    d(TAG, "Unsuccessful request")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.PhoneNumberError ->{
                    d(TAG, "Unsuccessful request")
                    binding.phoneNumberField.error = it.message
                }
                is Resource.AmountError ->{
                    d(TAG, "Unsuccessful request")
                    binding.amountField.error = it.message
                }
                is Resource.Loading ->{
                    d(TAG, "loading request")
                }
            }
        })
    }

    fun View.hideSoftKeyboard(){
        val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        if(imm.isAcceptingText){
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

    }

    fun validatePhoneNumber(validPhoneNumber: String, inputLayout: TextInputLayout): Boolean{

        return if(validPhoneNumber.isEmpty()){
            inputLayout.error = "Phone number cannot be empty"
            inputLayout.errorIconDrawable = null
            false
        }
        else if(validPhoneNumber.matches(phoneNumberPattern.toRegex())){
            i("Buy airtime fragment", "valid phone number $validPhoneNumber")
            true
        }
        else{
            inputLayout.error = "Invalid phone number"
            inputLayout.errorIconDrawable = null
            false
        }
    }

    fun validateAmount(amount: String, inputLayout: TextInputLayout): Boolean{
        val stringBuilder = StringBuilder(amount)
        while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
            stringBuilder.deleteCharAt(0)
        }
        val validAmount = stringBuilder.toString()
        i("Buy Airtime Fragment", "Valid amount $validAmount")
        return if( validAmount.isEmpty()){
            inputLayout.error = "Amount cannot be empty"
            false
        } else if( validAmount.toInt() < 10){
            inputLayout.error = "Amount cannot be less than Ksh 10"
            false
        }
        else {
            true
        }
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                v.hideSoftKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun showRationaleDialog(
        title: String,
        message: String,
        negativeButton: String,
        postiveButton: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(postiveButton, listener)
            .setNegativeButton(negativeButton) { dialog, which ->
                d(TAG, "Dialogue cancelled")
            }
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkPermissions(): Boolean{
        val permissionState = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(){
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_CONTACTS )
        if(shouldProvideRationale){
            d("Contacts Permission", "Providing permission rationale to give user more info")
            showRationaleDialog(
                "We need permission to read your contacts",
                "This will allow you to search a contact from your contacts instead of entering their number. ",
                "CANCEL",
                "OK"
            ) { dialog, which ->
                startReadContactsPermissionRequest()
            }
        }else{
            d("Contacts Permission", "Requesting READ_CONTACTS Permission")
            startReadContactsPermissionRequest()
        }
    }
    private fun startReadContactsPermissionRequest() {
        requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        d(TAG, "OnRequestPermissionResult")
        d(TAG, "Request code: $requestCode")
        d(TAG, "Grant Results is empty ${grantResults.isEmpty()}")
        d(TAG, "Permission granted ${grantResults[0] == PackageManager.PERMISSION_GRANTED}")
        if(requestCode == REQUEST_PERMISSIONS_REQUEST_CODE){
            when{
                grantResults.isEmpty() -> {
                    d(TAG, "User interaction was cancelled")
                }
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    pickContact()
                }else ->{
                d(TAG, "Permission denied")
                showRationaleDialog("Permission denied",
                    "Search contacts requires permission to access your contacts. To give this app permission to access contacts, go to settings -> Permissions and turn contacts permission on",
                    "CANCEL",
                    "SETTINGS"
                ) { dialog, which ->
                    requireContext().openAppSystemSettings()
                }
            }
            }
        }
    }

    fun Context.openAppSystemSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    private fun pickContact(){
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){

            if(requestCode == CONTACT_PICK_CODE){
                binding.phoneNumberToTopup.text = null
                val cursor1: Cursor
                val cursor2: Cursor?

                val uri = data?.data
                cursor1 = uri?.let { activity?.contentResolver?.query(it, null, null, null,null) }!!
                cursor1.moveToFirst()
                if(cursor1.moveToFirst()){
                    //get contact details
                    val contactId = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val idResults = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val idResultHold = idResults.toInt()

                    //check if contact has a phone number or not
                    if (idResultHold == 1){
                        cursor2 = activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+contactId,
                            null,
                            null
                        )
                        //a contact may have multiple phone numbers
                        val phoneNumbers = ArrayList<String>()
                        while (cursor2!!.moveToNext()){
                            //get phone number
                            val contactNumber = cursor2.getString(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            //set phone number
                            phoneNumbers.add(contactNumber)
                        }
                        if(phoneNumbers.size > 1){
                            showPhoneNumberDialog(phoneNumbers = phoneNumbers){ dialog, which ->
                                binding.phoneNumberToTopup.setText(getDigitsOnlyContact(phoneNumbers[which]))
                            }
                        }else if(phoneNumbers.size == 1){
                            binding.phoneNumberToTopup.setText(getDigitsOnlyContact(phoneNumbers[0]))
                        }
                        else{
                            Toast.makeText(activity, "No Phone Number found", Toast.LENGTH_LONG).show()

                        }

                        cursor2.close()
                    }
                    cursor1.close()
                }

            }
        }
        else {
            //cancelled picking contact
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPhoneNumberDialog(phoneNumbers: ArrayList<String>, listener: DialogInterface.OnClickListener) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val checkedItem = -1
        builder.setSingleChoiceItems(phoneNumbers.toTypedArray(), checkedItem, listener)
            .setPositiveButton("OK"){dialog, which ->
            d(TAG, "oK PRESSED")
        }
        builder.setTitle("Select a Phone Number")
            .setNegativeButton("Cancel") { dialog, which ->
                binding.phoneNumberToTopup.setText("")
                d(TAG, "Dialogue cancelled")
            }

        builder.create().show()
    }


    fun normalizePhoneNumber(phoneNumber: String): String{
        val validPhoneNumber = "0${getDigitsOnlyContact(phoneNumber)}"
        d(TAG, "Number after normalization: $validPhoneNumber")
        return validPhoneNumber
    }

    fun getDigitsOnlyContact(rawNumber: String): String{
        val re = Regex("[^0-9]")
        val digitsOnlyNumber = rawNumber.replace(re, "")
        val stringBuilder = StringBuilder(digitsOnlyNumber)
        d(TAG, "Number without white space: $digitsOnlyNumber")
        while(stringBuilder.isNotEmpty() && stringBuilder.length > 9 ){
            stringBuilder.deleteCharAt(0)
        }
        return stringBuilder.toString()
    }
}