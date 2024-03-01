package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Orange
import com.example.calculator.ui.theme.red
import com.example.calculator.ui.theme.yellow
import kotlin.math.ceil
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Calculator()
    }
}

@Composable
fun Calculator() {
    var calculation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var switchValue by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Divider(modifier= Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(bottom = 20.dp))
                Text(
                    text = calculation,
                    fontSize = 24.sp,
                    color = red
                )
            Divider(modifier= Modifier
                .fillMaxWidth()
                .height(2.dp))
            Text(
                text = result,
                fontSize = 24.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Round it up",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = switchValue,
                    onCheckedChange = {
                        // Update the switch value when changed
                        switchValue = it
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        Divider(modifier= Modifier
            .fillMaxWidth()
            .height(2.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "(", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("(", calculation) }
            CalculatorButton(text = ")", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter(")", calculation) }
            CalculatorButton(text = "⌫", yellow) { calculation = removeLastCharacter(calculation) }
            CalculatorButton(text = "/", Orange) { calculation = appendCharacter("/", calculation) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "7", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("7", calculation) }
            CalculatorButton(text = "8", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("8", calculation) }
            CalculatorButton(text = "9", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("9", calculation) }
            CalculatorButton(text = "×", Orange) { calculation = appendCharacter("*", calculation) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "4", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("4", calculation) }
            CalculatorButton(text = "5", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("5", calculation) }
            CalculatorButton(text = "6", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("6", calculation) }
            CalculatorButton(text = "-", Orange) { calculation = appendCharacter("-", calculation) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "1", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("1", calculation) }
            CalculatorButton(text = "2", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("2", calculation) }
            CalculatorButton(text = "3", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("3", calculation) }
            CalculatorButton(text = "+", Orange) { calculation = appendCharacter("+", calculation) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "0", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter("0", calculation) }
            CalculatorButton(text = ".", MaterialTheme.colorScheme.onBackground) { calculation = appendCharacter(".", calculation) }
            CalculatorButton(text = "=", yellow) {
                //result = calculateResult(calculation)
                if (switchValue){
                    result = calculateResult(calculation,true)
                }
                else{
                    result = calculateResult(calculation,false)
                }
            }
            CalculatorButton(text = "√", Orange) { result = calculateRoot(calculation) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CalculatorButton(text = "Clear", yellow, modifier = Modifier.fillMaxWidth()) {
            calculation = ""
            result = ""
        }
    }
}

fun removeLastCharacter(calculation: String): String {
    // Remove the last character from the calculation string
    return if (calculation.isNotEmpty()) calculation.dropLast(1) else calculation
}

@Composable
fun CalculatorButton(
    text: String,
    myColor:Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .width(64.dp)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = myColor)
    ) {
        Text(text = text,
            fontWeight = FontWeight.Bold)
    }
}

fun appendCharacter(character: String, calculation: String): String {
    // Append the character to the calculation string
    // You might want to handle edge cases here, like not allowing two operators in a row
    // For simplicity, let's assume here that we don't handle such edge cases
    return calculation + character
}

fun calculateResult(calculation: String, roundOff: Boolean): String {
    return try {
        // Check if parentheses are balanced
        if (!areParenthesesBalanced(calculation)) {
            return "Error: Unbalanced parentheses"
        } else {
            // Evaluate nested expressions first
            var modifiedCalculation = calculation
            while (modifiedCalculation.contains("(")) {
                val startIndex = modifiedCalculation.lastIndexOf("(")
                val endIndex = modifiedCalculation.indexOf(")", startIndex)
                val innerExpression = modifiedCalculation.substring(startIndex + 1, endIndex)
                val innerResult = calculateResult(innerExpression, roundOff)
                modifiedCalculation =
                    modifiedCalculation.replace("($innerExpression)", innerResult)
            }

            // Split the modified calculation string based on operators
            val parts = modifiedCalculation.split(Regex("(?<=[+\\-*/])|(?=[+\\-*/])"))

            // Initialize result with the first part of the calculation
            var result = parts[0].toDouble()

            // Iterate over the parts of the calculation and perform the operations
            for (i in 1 until parts.size step 2) {
                val operator = parts[i]
                val operand = parts[i + 1].toDouble()

                when (operator) {
                    "+" -> result += operand
                    "-" -> result -= operand
                    "*" -> result *= operand
                    "/" -> {
                        result /= operand
                        // Round off the result if the roundOff flag is true
                        if (roundOff) {
                            result = ceil(result)
                        }
                    }
                }
            }

            result.toString()
        }
    } catch (e: Exception) {
        "Error: Invalid expression"
    }
}




fun areParenthesesBalanced(expression: String): Boolean {
    var openCount = 0
    expression.forEach {
        when (it) {
            '(' -> openCount++
            ')' -> {
                if (openCount == 0) return false
                else
                    openCount--
                return true
            }
        }
    }
    return openCount == 0
}

fun calculateRoot(calculation: String): String {
    return try {
        val value = calculation.toDouble()
        val result = sqrt(value)
        result.toString()
    } catch (e: Exception) {
        "Error: Invalid expression"
    }
}
