# CDA: Composite Delegate Adapter in Kotlin 


CDA Lib is a simple delegate-pattern to build Adapters of the recycler view in composition instead of inherantence (which is the common way in android to build adatper by extending from Adapter classes). 
Also the lib includes some conveniece methods to easly notify calculate and dispatch updates to the adapters by using the DiffUtils. 

Using this libs should speed up your development and reduce the changes of getting a good class adapters. With CDA you only concernt about the fact of building the ViewHolders you want to include in the adapter and adding the data that it represents. No switches, not adapters, no need to notify updates manually. 


### Version 1.0.1 

First release of the lib, waiting for feedback. 
Including: 
- Composite delegate Adapter CompositeDelegateAdapter
- Methods to notify the adapter by using DiffUtils internally 
- Contract to represent UI models that the adapter will show (RecyclerViewType) 



### Getting stared 

#### Setting up dependecy 
