package com.sliit.easylearner.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "easy_learn_tamil";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists assignment_status" + " (id INTEGER PRIMARY KEY AUTOINCREMENT,assignment_id integer, total_marks integer, status integer)");
        db.execSQL("create table if not exists assignment_marks" + " (id INTEGER PRIMARY KEY AUTOINCREMENT,assignment_id integer, question_number integer, time integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS assignment_status");
        db.execSQL("DROP TABLE IF EXISTS assignment_marks");
        onCreate(db);
    }

    public boolean insertAssignmentTotalMarks(int assignmentId, int totalMarks, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("assignment_id",assignmentId);
        cv.put("total_marks",totalMarks);
        cv.put("status",status);
        db.insert("assignment_status", null, cv);
        return true;
    }

    public boolean insertAssignmentTime(int assignmentId, int number, int time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("assignment_id",assignmentId);
        cv.put("question_number",number);
        cv.put("time",time);
        db.insert("assignment_marks", null, cv);
        return true;
    }

    public boolean checkUserAssignmentStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor assignment1_res = db.rawQuery("select * from assignment_status where assignment_id=1", null);
        int assignment1_status = 0;
        if(assignment1_res.moveToFirst())
        {
            assignment1_status = assignment1_res.getInt(assignment1_res.getColumnIndex("status"));
        }
        assignment1_res.close();

        Cursor assignment2_res = db.rawQuery("select * from assignment_status where assignment_id=2", null);
        int assignment2_status = 0;
        if(assignment2_res.moveToFirst())
        {
            assignment2_status = assignment2_res.getInt(assignment2_res.getColumnIndex("status"));
        }
        assignment2_res.close();

        if ((assignment1_status == 1) && (assignment2_status ==1)){
            return true;
        }else return false;
    }

    public void deleteallAssignmentStatusAndMarks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from assignment_status");
        db.execSQL("delete from assignment_marks");
    }

    public Cursor getallAssignment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from assignment_status", null);
        return res;
    }

    public Cursor getallAssignmentDuration() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from assignment_marks", null);
        return res;
    }

    public boolean checkPaperAttemptStatus(int assignmentId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor assignment = db.rawQuery("select * from assignment_status where assignment_id="+assignmentId, null);
        if (assignment.getCount() > 0){
            return false;
        }else {
            return true;
        }
    }
}
