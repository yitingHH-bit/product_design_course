
package com.example.ex_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.room.Room

// ---------------------------------------------------------------------
// ROOM DATABASE FOR SHOPPING LIST
// ---------------------------------------------------------------------

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quantity: Int,
    val unit: String,
    val price: Double
)

@Dao
interface ShoppingDao {

    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem ORDER BY id DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>
}

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}

// ---------------------------------------------------------------------
// VIEWMODEL
// ---------------------------------------------------------------------

class ShoppingViewModel(private val db: ShoppingDatabase) : ViewModel() {
    private val dao = db.shoppingDao()
    val items: Flow<List<ShoppingItem>> = dao.getAllItems()

    suspend fun addItem(name: String, qty: Int, unit: String, price: Double) {
        dao.insertItem(ShoppingItem(name = name, quantity = qty, unit = unit, price = price))
    }

    suspend fun delete(item: ShoppingItem) {
        dao.deleteItem(item)
    }
}

class ShoppingViewModelFactory(private val db: ShoppingDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}

// ---------------------------------------------------------------------
// REMEMBER DATABASE INSTANCE
// ---------------------------------------------------------------------

@Composable
fun rememberShoppingDb(): ShoppingDatabase {
    val context = LocalContext.current
    return remember {
        Room.databaseBuilder(
            context.applicationContext,
            ShoppingDatabase::class.java,
            "shopping-db"
        ).build()
    }
}

// ---------------------------------------------------------------------
// UI
// ---------------------------------------------------------------------

@Composable
fun ShoppingApp() {
    val db = rememberShoppingDb()
    val vm: ShoppingViewModel = viewModel(factory = ShoppingViewModelFactory(db))
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val list by vm.items.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Shopping List", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        // INPUT FIELDS
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = qty,
                onValueChange = { qty = it },
                label = { Text("Quantity") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = unit,
                onValueChange = { unit = it },
                label = { Text("Unit") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    scope.launch {
                        vm.addItem(
                            name = name,
                            qty = qty.toIntOrNull() ?: 0,
                            unit = unit,
                            price = price.toDoubleOrNull() ?: 0.0
                        )
                        name = ""; qty = ""; unit = ""; price = ""
                    }
                }
            }
        ) {
            Text("Add Item")
        }

        Spacer(Modifier.height(20.dp))

        // TABLE HEADER
        Row(
            Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp)
        ) {
            Text("Item", Modifier.weight(2f), color = Color.White)
            Text("Qty", Modifier.weight(1f), color = Color.White)
            Text("Unit", Modifier.weight(1f), color = Color.White)
            Text("Price", Modifier.weight(1f), color = Color.White)
            Text("Del", Modifier.weight(1f), color = Color.White)
        }

        // TABLE ROWS
        LazyColumn {
            items(list) { item ->
                ShoppingRow(item = item, onDelete = {
                    scope.launch { vm.delete(item) }
                })
            }
        }
    }
}

@Composable
fun ShoppingRow(item: ShoppingItem, onDelete: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
    ) {
        Text(item.name, Modifier.weight(2f).padding(4.dp))
        Text(item.quantity.toString(), Modifier.weight(1f).padding(4.dp))
        Text(item.unit, Modifier.weight(1f).padding(4.dp))
        Text("%.2f".format(item.price), Modifier.weight(1f).padding(4.dp))

        Button(onClick = onDelete, Modifier.weight(1f)) {
            Text("X")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShoppingApp() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShopping() {
    ShoppingApp()
}
