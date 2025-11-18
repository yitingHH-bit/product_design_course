
package com.example.ex_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ex_1.ui.theme.Ex_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductBannerScreen()
                }
            }
        }
    }
}

@Composable
fun ProductBannerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🔹 Background product image from res/drawable
        Image(
            painter = painterResource(id = R.drawable.image1), // use image1.jpg (or any image you have)
            contentDescription = "Product image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔹 Overlay at the bottom with product info
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color(0xAA000000)) // semi-transparent black
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "UltraSound Pro X200",          // product name
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "SoundWave Audio Ltd.",          // company name
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "Contact: info@soundwave.com",   // contact info line 1
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = "Phone: +358 123 456 789",       // contact info line 2
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductBannerPreview() {
    Ex_1Theme {
        ProductBannerScreen()
    }
}
