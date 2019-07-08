package com.example.week5weekend

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME,factory, VERSION)
{
    companion object{
        const val DATABASE_NAME="Employee"
        const val VERSION=1;
        const val TABLE_NAME="thins"
        const val COLLUM_FIRST="firstname"
        const val COLLUM_LAST="lastname"
        const val COLLUM_ADDRESS="address"
        const val COLLUM_CITY="city"
        const val COLLUM_STATE="state"
        const val COLLUM_ZIP="zip"
        const val COLLUM_TAX="tax"
        const val COLLUM_POSITION="position"
        const val COLLUM_DEPARTMENT="department"



    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE=("CREATE TABLE "+ TABLE_NAME+"("+
                COLLUM_TAX+ " TEXT PRIMARY KEY," + COLLUM_FIRST + " TEXT,"+ COLLUM_LAST+ " TEXT,"
                + COLLUM_ADDRESS+" TEXT,"+ COLLUM_CITY+" TEXT,"+ COLLUM_STATE+" TEXT,"+ COLLUM_ZIP+" TEXT,"
                + COLLUM_POSITION+" TEXT,"+ COLLUM_DEPARTMENT+" TEXT)"
                )
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(db)
    }
    fun addEmployee(employee: Employee){
        val values = ContentValues();
        values.put(COLLUM_TAX,employee.taxid)
        values.put(COLLUM_FIRST,employee.firstname)
        values.put(COLLUM_LAST,employee.lastname)
        values.put(COLLUM_ADDRESS,employee.address)
        values.put(COLLUM_CITY,employee.city)
        values.put(COLLUM_ZIP,employee.zip)
        values.put(COLLUM_STATE,employee.state)
        values.put(COLLUM_POSITION,employee.position)
        values.put(COLLUM_DEPARTMENT,employee.department)
        val db=this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()

    }
    fun getOne(employee: Employee){
        lateinit var returnEmployee:Employee
        var taxgot=employee.taxid
        var db=this.readableDatabase
        var cursor=db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLLUM_TAX = \"$taxgot\"", null)


    }
    fun getall(): ArrayList<Employee>?{
        var returnEmployees: ArrayList<Employee>? =null;
        var db=this.readableDatabase
        var cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME, null)
        if(cursor.moveToFirst()){
            do{
                var first=cursor.getString(cursor.getColumnIndex(COLLUM_FIRST))
                var last=cursor.getString(cursor.getColumnIndex(COLLUM_LAST))
                var address=cursor.getString(cursor.getColumnIndex(COLLUM_ADDRESS))
                var city=cursor.getString(cursor.getColumnIndex(COLLUM_CITY))
                var state=cursor.getString(cursor.getColumnIndex(COLLUM_STATE))
                var zip=cursor.getString(cursor.getColumnIndex(COLLUM_ZIP))
                var tax=cursor.getString(cursor.getColumnIndex(COLLUM_TAX))
                var position=cursor.getString(cursor.getColumnIndex(COLLUM_POSITION))
                var department=cursor.getString(cursor.getColumnIndex(COLLUM_DEPARTMENT))
                var employee=Employee(first,last,address,city,state,zip,tax,position, department)
                returnEmployees?.add(employee)
            } while (cursor.moveToNext())



        }
        return returnEmployees;
    }

}