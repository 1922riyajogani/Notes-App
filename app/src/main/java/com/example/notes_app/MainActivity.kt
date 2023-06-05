package com.example.notes_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes_app.Adapter.NotesAdapter
import com.example.notes_app.Database.RoomDB
import com.example.notes_app.Entity.NoteEntity
import com.example.notes_app.databinding.ActivityMainBinding
import com.example.notes_app.databinding.AddDialogeBinding
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var db: RoomDB
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)

         initView()

    }

    private fun initView() {
        binding.add.setOnClickListener{
            addNoteDialoge()
        }

        adapter = NotesAdapter(db.note().getNotes())
        binding.noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = adapter

    }

    private fun addNoteDialoge(){
        var dialog = Dialog(this)
        var bind = AddDialogeBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.btnsubmit.setOnClickListener {
            var title = bind.edttitle.text.toString()
            var text = bind.edttext.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a ")
            var current = format.format(Date())
            var data = NoteEntity(title,text,current)
            db.note().addNote(data)
            adapter.update(db.note().getNotes())
            dialog.dismiss()
        }

        dialog.show()
    }
}