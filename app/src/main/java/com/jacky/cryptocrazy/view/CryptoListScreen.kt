package com.jacky.cryptocrazy.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jacky.cryptocrazy.model.CryptoItem
import com.jacky.cryptocrazy.model.CryptoListItem
import com.jacky.cryptocrazy.viewmodel.CryptoListViewModel

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Crypto Crazy",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search ...", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.DarkGray, CircleShape)
            ) {
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CryptoList(navController = navController)

        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, hint: String = "", onSearch: (String) -> Unit = {}) {
    var text = remember {
        mutableStateOf("")
    }

    var isHintDisplayed = remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text.value, onValueChange = {
                text.value = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed.value = it.isFocused != true && text.value.isEmpty()
                },
            cursorBrush = SolidColor(Color.White)
        )

        if (isHintDisplayed.value) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }


}


@Composable
fun CryptoList(navController: NavController, viewModel: CryptoListViewModel = hiltViewModel()) {

    val cryptoList = remember {
        viewModel.cryptoList
    }
    val errorMessage = remember {
        viewModel.errorMessage
    }
    val isLoading = remember {
        viewModel.isLoading
    }

    CryptoListView(cryptos = cryptoList.value, navController = navController)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading.value) {
            CircularProgressIndicator()
        }
        if (errorMessage.value.isNotEmpty()) {
            println(errorMessage.value)
            retryView(error = errorMessage.value) {
                viewModel.getAllCrypto()
            }
        }
    }

}

@Composable
fun CryptoListView(cryptos: List<CryptoListItem>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}


@Composable
fun CryptoRow(navController: NavController, crypto: CryptoListItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                navController.navigate("crypto_detail_screen/${crypto.currency}/${crypto.price}")
            }
    ) {
        Text(
            text = crypto.currency,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = crypto.price,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(2.dp),
            color = Color.Black
        )
    }
}

@Composable
fun retryView(
    error: String,
    onRetry: () -> Unit
) {
    Column() {
        Text(text = error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}


