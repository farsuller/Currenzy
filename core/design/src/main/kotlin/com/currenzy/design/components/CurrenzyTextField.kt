package com.currenzy.design.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currenzy.design.theme.CurrenzyTheme

@Composable
fun CurrenzyTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .background(Color(0xFFEFFEFE))
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),
        textStyle = MaterialTheme.typography.labelLarge,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine
    )
}

@Preview
@Composable
fun CurrenzyTextFieldPreview() {
    CurrenzyTheme {
        CurrenzyTextField(value = "1500", onValueChange = {})
    }

}