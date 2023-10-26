package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}
private fun calculateTip(amount: Double,
                         tipPercent: Double = 15.0,
                         roundUp: Boolean
): String{
    var tip = amount * tipPercent / 100
    if(roundUp)
        tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    var tipInput by remember{mutableStateOf("")}
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    var roundUp by remember {mutableStateOf(false)}

    val tip = calculateTip(amount, tipPercent, roundUp )

    Column(modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text( text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChange = {amountInput = it},
            label = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            value = tipInput,
            onValueChange = {tipInput = it},
            label = R.string.tip_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(roundUp = roundUp,
            onRoundUpChange = {roundUp = it} ,
            modifier = Modifier.padding(bottom = 32.dp))
        Text( text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
            )

    }
}


@Composable
fun EditNumberField(value: String,
                    onValueChange:(String)-> Unit,
                    @StringRes label: Int,
                    keyboardOptions: KeyboardOptions,
                    modifier: Modifier = Modifier){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = stringResource(R.string.bill_amount))},
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}
@Composable
fun RoundTheTipRow(roundUp: Boolean,
                   onRoundUpChange: (Boolean) -> Unit ,
                   modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}


//AQUI EL BOTON DEL DADO
/*@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember{mutableStateOf(1)}
    var imageResource = when (result){
        1-> R.drawable.dice_1
        2-> R.drawable.dice_2
        3-> R.drawable.dice_3
        4-> R.drawable.dice_4
        5-> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(imageResource),
            contentDescription = "1"
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {result = (1..6).random() }){
            Text(text = stringResource(R.string.roll))
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}*/
