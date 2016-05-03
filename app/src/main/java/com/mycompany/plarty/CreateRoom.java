package com.mycompany.plarty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class CreateRoom extends Activity {

    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.room_create);

        TextView roomCode = (TextView) findViewById(R.id.roomCode);
        roomCode.append(generateRoomCode());
        // TODO - add room password so the code is unique and shit
    }

    private String generateRoomCode() {
        StringBuilder code = new StringBuilder(RANDOM_STRING_LENGTH);
        Random rand = new Random();

        for (int i = 0; i < RANDOM_STRING_LENGTH; i += 1){
            int tmp = (rand.nextInt() % CHAR_LIST.length());
            if (tmp < 0) {
                tmp = -tmp;
            }
            code.append(CHAR_LIST.charAt(tmp));
        }

        return code.toString();
    }

    public void onCreateRoom(View view) {

        EditText roomNameET = (EditText) findViewById(R.id.roomName);
        TextView roomCodeTV = (TextView) findViewById(R.id.roomCode);

        String roomName = String.valueOf(roomNameET.getText());
        String roomCode = String.valueOf(roomCodeTV.getText());

        Intent hostRoom = new Intent();
        hostRoom.putExtra("RoomName", roomName);
        hostRoom.putExtra("RoomCode", roomCode);
        setResult(RESULT_OK, hostRoom);

        finish();
    }
}
