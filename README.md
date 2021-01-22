# CDA: Composite Delegate Adapter in Kotlin 


CDA Lib is a simple delegate-pattern to build Adapters of the recyclerview in composition instead of inheritance (which is the common way in android to build adatper by extending from Adapter classes). 
Also the lib includes some convenience methods to easily notify calculate and dispatch updates to the adapters by using the DiffUtils.

Using this libs you should speed up your development and reduce the changes of getting a long classes adapters. With CDA you only concernt about the fact of building the ViewHolders you want to include in the adapter and adding the data that it represents. No switches, not adapters, no need to notify updates manually. 


### Version 1.0.2

First release of the lib, waiting for feedback. 
Including: 
- Composite delegate Adapter CompositeDelegateAdapter
- Methods to notify the adapter by using DiffUtils internally 
- Contract to represent UI models that the adapter will show (RecyclerViewType) 



### Getting stared 

#### Setting up dependecy 

```
    implementation 'com.ingjuanocampo:cda:XXX'
```

Please replace the `XXX` with the latest version. 


Also include this into the repositories list 

```
  maven {
           url  "https://dl.bintray.com/ingjuanocampo/CDA"
       }
```

It should ended up looking like this: 

```
allprojects {
     repositories {
         google()
         jcenter()
         maven {
             url  "https://dl.bintray.com/ingjuanocampo/CDA"
         }
     }
```


#### Terminology 

1. CompositeDelegateAdapter: Is the Adapter you will set to your recyclerview. It receives in the constructos the size of the viewholders your adapter will support as an interger. 
```kotlin
        val adapter = CompositeDelegateAdapter(10)
```

The class extends from the RecyclerView.Adapter and it is open to extension (Please avoid it, keep you adapters easy and clean) 


This adapter uses to renderize a lisf of DelegateViewHolder, and you can add them so the CompositeDelegateAdapter. 

Use the method `appendDelegate(...)`  to append a DelegateViewHolder, by also including the viewType id that it is related to. 

for example; 
```kotlin
        adapter.appendDelegate(DelegateViewTypes.COLOR_VIEW_TYPE.ordinal) { ColorItemViewHolder(it) }
```
2. RecyclerViewType is a contract interface to bind the items in the adapter to their repective DelegateViewHolder. 

```kotlin
interface RecyclerViewType {
    fun getDelegateId(): Int
    fun getViewType(): Int
}
```


Implement this and provide a unique ID and a viewTypeId both as Intergers. 

For example; 


```kotlin
data class ColorRecyclerViewType(val text: String, val textColor: Int, val bkg: Int):
    RecyclerViewType {
    override fun getDelegateId(): Int {
        return text.hashCode()
    }

    override fun getViewType(): Int = DelegateViewTypes.COLOR_VIEW_TYPE.ordinal
}
```

Please be aware that the model you will use here should represent what need to be bound into the ViewHolder. 

3. DelegateViewHolder: This is class extends from `RecyclerView.ViewHolder` and it the one in charge to bind the information of the assigned `RecyclerViewType` when the adapter binds the for that particular position. 

For example: 

``` kotlin 
data class ColorItemViewHolder( val parent: ViewGroup):
    DelegateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.delegate_color, parent, false)) {

    private val tvDelegate = itemView.findViewById<TextView>(R.id.tvDelegate)
    private val lnDelegate = itemView.findViewById<LinearLayout>(R.id.lnDelegate)

    override fun onBindViewHolder(recyclerViewType: RecyclerViewType) {
        recyclerViewType as ColorRecyclerViewType
        tvDelegate.text = recyclerViewType.text
        tvDelegate.setTextColor(itemView.resources.getColor(recyclerViewType.textColor))
        lnDelegate.setBackgroundColor(itemView.resources.getColor(recyclerViewType.bkg))
    }

}
``` 




#### Say goodbye to Inheritance Adapter, Say hello to CDA! 

Finally, to set items into the adapter, you can just something like this 


```kotlin

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        
        val adapter = CompositeDelegateAdapter(1)

        adapter.appendDelegate(DelegateViewTypes.COLOR_VIEW_TYPE.ordinal) { ColorItemViewHolder(it) }

        recyclerView.adapter = adapter

        adapter.addNewItem(ColorRecyclerViewType(
                "Test 1",
                R.color.white,
                R.color.design_default_color_error
        ))

        adapter.addNewItem(ColorRecyclerViewType(
                "Test 2",
                R.color.black,
                R.color.white
        ))
        adapter.addNewItem(ColorRecyclerViewType(
                "Test 3",
                R.color.purple_200,
                R.color.teal_700
        ))
        adapter.addNewItem(ColorRecyclerViewType(
                "Test 4",
                R.color.design_default_color_primary,
                R.color.design_default_color_secondary
        ))
        adapter.addNewItem(ColorRecyclerViewType(
                "Test 5",
                R.color.material_on_background_disabled,
                R.color.design_default_color_on_secondary
        ))
```

In the above example, there is only one `DelegateViewHolder` as the `ColorItemViewHolder` which renderize `ColorRecyclerViewType` with some scential information. 

The `ColorRecyclerViewType` uses a simple interger as recycler view type, in this case `DelegateViewTypes.COLOR_VIEW_TYPE.ordinal`, so as you may observe, the method `adapter.appendDelegate(id) { }` recieves the same id of the `RecyclerViewType` that will represent the data. Also the method receives the `DelegateViewHolder` as a lambda fuction to be executed by the adapter. 


Enjoy it! :) and any feedback is welcome! 








