package kg.izapp.testapp.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.izapp.testapp.R
import kg.izapp.testapp.data.Event
import kg.izapp.testapp.data.TestApiHelper
import kg.izapp.testapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var apihelper: TestApiHelper
    private var progressBarIsShowing = false

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        apihelper = TestApiHelper()
        subscribeToLiveData()
        setupViews()
    }

    @SuppressLint("RestrictedApi")
    private fun subscribeToLiveData() {
        apihelper.event.observe(this, Observer {
            when (it) {
                is Event.InCorrectData -> {
                    progressBarIsShowing = false
                    loading.visibility = View.GONE
                    Toast.makeText(
                        this,
                        getString(R.string.label_incorrect_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Event.SuccessLogin -> {
                    progressBarIsShowing = false
                    loading.visibility = View.GONE
                    MainActivity.start(this)
                }
            }
        })
    }

    override fun onBackPressed() {
        Thread.currentThread().interrupt()
        super.onBackPressed()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean("progressBarIsShowing")) {
            loading.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        if (progressBarIsShowing) {
            outState.putBoolean("progressBarIsShowing", progressBarIsShowing)
        }
    }

    private fun setupViews() {
        inp_username.addTextChangedListener(getTextWatcher())
        inp_password.addTextChangedListener(getTextWatcher())
        login.setOnClickListener {
            progressBarIsShowing = true
            loading.visibility = View.VISIBLE
            login.postDelayed({
                apihelper.isValidData(
                    username.editText?.text.toString().trim(),
                    password.editText?.text.toString().trim()
                )
            }, 0)
        }
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                toggleButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun toggleButtonState() {
        login.isEnabled = username.editText?.text.toString().trim().isNotEmpty()
                && password.editText?.text.toString().trim().isNotEmpty()
        password.error = ""
    }

}