import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TestCompose(modifier: Modifier = Modifier){
    Text(text = "Hello from Module Design")
}

@Preview(showBackground = true)
@Composable
fun TestPreview(){
    TestCompose()
}