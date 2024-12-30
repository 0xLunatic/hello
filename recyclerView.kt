// 1. Data Model (Item.kt)
data class Item(val title: String, val description: String)

// 2. Adapter (ItemAdapter.kt)
class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.titleTextView.text = item.title
        holder.descriptionTextView.text = item.description
    }

    override fun getItemCount(): Int = itemList.size
}

// 3. RecyclerView Item Layout (item_layout.xml)
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="10dp"
    android:marginBottom="10dp">

    <TextView
        android:id="@+id/item_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Title"
        android:textSize="18sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/item_description"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Description" />
</LinearLayout>

// 4. Activity Setup (MainActivity.kt)
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Sample data
        itemList = listOf(
            Item("Item 1", "Description for Item 1"),
            Item("Item 2", "Description for Item 2"),
            Item("Item 3", "Description for Item 3")
        )

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter
    }
}

// 5. Dependencies (in build.gradle)
dependencies {
    implementation "androidx.recyclerview:recyclerview:1.3.0"
}
