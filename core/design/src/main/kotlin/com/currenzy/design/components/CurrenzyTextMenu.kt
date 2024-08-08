package com.currenzy.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currenzy.design.theme.CurrenzyTheme

@Composable
fun CurrenzyTextMenu(
    modifier: Modifier = Modifier,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it })
    {
        Row(
            modifier = Modifier.menuAnchor(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = selectedOption, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.graphicsLayer {
                    rotationZ = if (expanded) 180F else 0F
                })

        }

        ExposedDropdownMenu(
            modifier = Modifier
                .width(150.dp)
                .height(300.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false })
        {
            LazyColumn(
                modifier = Modifier
                    .width(150.dp)
                    .height(300.dp)
            ) {
                itemsIndexed(options) { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onOptionSelected(index)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrenzyTextMenuPreview() {
    CurrenzyTheme {
        CurrenzyTextMenu(
            selectedOption = "Select Currency",
            options = listOf("USD", "PHP", "INR"),
        ) {
        }
    }

}