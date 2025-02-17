package com.example.ecommerce.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()  // Ensure Firebase is properly initialized

    private val _banner = MutableLiveData<List<SliderModel>>()  // LiveData to hold banner data
    val banners: LiveData<List<SliderModel>> = _banner

    fun loadBanners() {
        val Ref = firebaseDatabase.getReference("Banner")  // Get a reference to the "Banner" node
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if(list!=null){
                        lists.add(list)
                    }
                }
                _banner.value=lists  // Post the list to the LiveData
            }

            override fun onCancelled(error: DatabaseError) {
                // Optionally log or handle database errors here
                // Log.e("FirebaseError", "Failed to load banners", error.toException())
            }
        })
    }
}
