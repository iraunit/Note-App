package com.shyptsolution.noteapp

class Note {
    var noteId:Int?=null
    var noteName:String?=null
    var noteDes:String?=null

    constructor(noteId:Int,noteName:String,noteDes:String){
        this.noteId=noteId
        this.noteDes=noteDes
        this.noteName=noteName
    }
}