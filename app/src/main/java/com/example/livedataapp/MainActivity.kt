package com.example.livedataapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livedataapp.ui.theme.LiveDataAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveDataAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {

    var value by remember {
        mutableStateOf("")
    }
    var state by remember { mutableStateOf(false) }
    val viewModel: CounterViewModel = viewModel()
    val myLiveDataValue by viewModel.myLiveData.observeAsState("")

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(15.dp)) {
        TextField(
            value = value,
            onValueChange = {
                value = it
            }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                state = !state
                viewModel.updateValue(value)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Set Value")
        }
        Spacer(modifier = Modifier.height(25.dp))

        if (state) Text(
            text = myLiveDataValue ?: "Default Value",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            state = true
            viewModel.changeValue()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Update Value")
        }
    }

}

class CounterViewModel : ViewModel() {
    val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> get() = _myLiveData
    var setChangeValue = mutableStateOf(false)
    fun updateValue(newValue: String) {
        _myLiveData.value = newValue
    }
    fun changeValue() {
        _myLiveData.value = "changed"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LiveDataAppTheme {
        Greeting()
    }
}