package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
//By:Diego Cobos
public class EnterAmountOfPeople extends AppCompatActivity {

    boolean [] unfilledDisplaying = new boolean [21];
    private String inputRoomNum;
    private String tripDay;
    private String formattedTripDate;
    private String tripID;
    private String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_amount_of_people);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }
        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getExtras();
        tripDay = getIntent().getStringExtra("excursionDate");
        formattedTripDate = getIntent().getStringExtra("formattedDate");
        tripID = getIntent().getStringExtra("savedTripID");
        tripName = getIntent().getStringExtra("tripName");
        inputRoomNum = getIntent().getStringExtra("roomNumber");




        for(int i = 0; i < 21; i++){
            unfilledDisplaying[i] = true;
        }

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterAmountOfPeople.this, TripHomePage.class));
            }
        });

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEnterNps = new Intent(EnterAmountOfPeople.this,EnterNPSPassID.class);
                toEnterNps.putExtra("excursionDate", tripDay);
                toEnterNps.putExtra("savedTripID", tripID);
                toEnterNps.putExtra("tripName", tripName);
                toEnterNps.putExtra("formattedDate", formattedTripDate);
                toEnterNps.putExtra("roomNumber", inputRoomNum);
                toEnterNps.putExtra("numOfPeople", guestNumber());


                startActivity(toEnterNps);
            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterAmountOfPeople.this, EnterGuestRoom.class));
            }
        });


        ImageView twoPeople = findViewById(R.id.btn2);
        twoPeople.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if(unfilledDisplaying[2] == false){
                                                 twoPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                 unfilledDisplaying[2] = true;
                                             }
                                             else{
                                                 for(int i = 0; i < 21; i++){
                                                     if(i == 2){
                                                         continue;
                                                     }
                                                     else{
                                                         if(unfilledDisplaying[i] != true){
                                                             Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                             toast.show();
                                                             return false;
                                                         }
                                                     }
                                                 }
                                                 twoPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                 unfilledDisplaying[2] = false;
                                             }
                                             return false;
                                         }
                                     }
        );

        ImageView fourPeople = findViewById(R.id.btn4);
        fourPeople.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              if(unfilledDisplaying[4] == false){
                                                  fourPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                  unfilledDisplaying[4] = true;
                                              }
                                              else{
                                                  for(int i = 0; i < 21; i++){
                                                      if(i == 4){
                                                          continue;
                                                      }
                                                      else{
                                                          if(unfilledDisplaying[i] != true){
                                                              Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                              toast.show();
                                                              return false;
                                                          }
                                                      }
                                                  }
                                                  fourPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                  unfilledDisplaying[4] = false;
                                              }

                                              return false;
                                          }
                                      }
        );

        ImageView sixPeople = findViewById(R.id.btn6);
        sixPeople.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if(unfilledDisplaying[6] == false){
                                                 sixPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                 unfilledDisplaying[6] = true;
                                             }
                                             else{
                                                 for(int i = 0; i < 21; i++){
                                                     if(i == 6){
                                                         continue;
                                                     }
                                                     else{
                                                         if(unfilledDisplaying[i] != true){
                                                             Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                             toast.show();
                                                             return false;
                                                         }
                                                     }
                                                 }
                                                 sixPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                 unfilledDisplaying[6] = false;
                                             }

                                             return false;
                                         }
                                     }
        );

        ImageView eightPeople = findViewById(R.id.btn8);
        eightPeople.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               if(unfilledDisplaying[8] == false){
                                                   eightPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                   unfilledDisplaying[8] = true;
                                               }
                                               else{
                                                   for(int i = 0; i < 21; i++){
                                                       if(i == 8){
                                                           continue;
                                                       }
                                                       else{
                                                           if(unfilledDisplaying[i] != true){
                                                               Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                               toast.show();
                                                               return false;
                                                           }
                                                       }
                                                   }
                                                   eightPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                   unfilledDisplaying[8] = false;
                                               }
                                               return false;
                                           }
                                       }
        );

        ImageView onePeople = findViewById(R.id.btn1);
        onePeople.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if(unfilledDisplaying[1] == false){
                                                 onePeople.setImageResource(R.drawable.drop_down_bar_small);
                                                 unfilledDisplaying[1] = true;
                                             }
                                             else{
                                                 for(int i = 0; i < 21; i++){
                                                     if(i == 1){
                                                         continue;
                                                     }
                                                     else{
                                                         if(unfilledDisplaying[i] != true){
                                                             Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                             toast.show();
                                                             return false;
                                                         }
                                                     }
                                                 }
                                                 onePeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                 unfilledDisplaying[1] = false;
                                             }
                                             return false;
                                         }
                                     }
        );

        ImageView threePeople = findViewById(R.id.btn3);
        threePeople.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               if(unfilledDisplaying[3] == false){
                                                   threePeople.setImageResource(R.drawable.drop_down_bar_small);
                                                   unfilledDisplaying[3] = true;
                                               }
                                               else{
                                                   for(int i = 0; i < 21; i++){
                                                       if(i == 3){
                                                           continue;
                                                       }
                                                       else{
                                                           if(unfilledDisplaying[i] != true){
                                                               Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                               toast.show();
                                                               return false;
                                                           }
                                                       }
                                                   }
                                                   threePeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                   unfilledDisplaying[3] = false;
                                               }
                                               return false;
                                           }
                                       }
        );

        ImageView fivePeople = findViewById(R.id.btn5);
        fivePeople.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              if(unfilledDisplaying[5] == false){
                                                  fivePeople.setImageResource(R.drawable.drop_down_bar_small);
                                                  unfilledDisplaying[6] = true;
                                              }
                                              else{
                                                  for(int i = 0; i < 21; i++){
                                                      if(i == 5){
                                                          continue;
                                                      }
                                                      else{
                                                          if(unfilledDisplaying[i] != true){
                                                              Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                              toast.show();
                                                              return false;
                                                          }
                                                      }
                                                  }
                                                  fivePeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                  unfilledDisplaying[5] = false;
                                              }
                                              return false;
                                          }
                                      }
        );

        ImageView sevenPeople = findViewById(R.id.btn7);
        sevenPeople.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               if(unfilledDisplaying[7] == false){
                                                   sevenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                   unfilledDisplaying[7] = true;
                                               }
                                               else{
                                                   for(int i = 0; i < 21; i++){
                                                       if(i == 7){
                                                           continue;
                                                       }
                                                       else{
                                                           if(unfilledDisplaying[i] != true){
                                                               Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                               toast.show();
                                                               return false;
                                                           }
                                                       }
                                                   }
                                                   sevenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                   unfilledDisplaying[7] = false;
                                               }
                                               return false;
                                           }
                                       }
        );

        ImageView ninePeople = findViewById(R.id.btn9);
        ninePeople.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              if(unfilledDisplaying[9] == false){
                                                  ninePeople.setImageResource(R.drawable.drop_down_bar_small);
                                                  unfilledDisplaying[9] = true;
                                              }
                                              else{
                                                  for(int i = 0; i < 21; i++){
                                                      if(i == 9){
                                                          continue;
                                                      }
                                                      else{
                                                          if(unfilledDisplaying[i] != true){
                                                              Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                              toast.show();
                                                              return false;
                                                          }
                                                      }
                                                  }
                                                  ninePeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                  unfilledDisplaying[9] = false;
                                              }
                                              return false;
                                          }
                                      }
        );

        ImageView tenPeople = findViewById(R.id.btn10);
        tenPeople.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View v, MotionEvent event) {
                                             if(unfilledDisplaying[10] == false){
                                                 tenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                 unfilledDisplaying[10] = true;
                                             }
                                             else{
                                                 for(int i = 0; i < 21; i++){
                                                     if(i == 10){
                                                         continue;
                                                     }
                                                     else{
                                                         if(unfilledDisplaying[i] != true){
                                                             Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                             toast.show();
                                                             return false;
                                                         }
                                                     }
                                                 }
                                                 tenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                 unfilledDisplaying[10] = false;
                                             }
                                             return false;
                                         }
                                     }
        );

        ImageView elevenPeople = findViewById(R.id.btn11);
        elevenPeople.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if(unfilledDisplaying[11] == false){
                                                    elevenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                    unfilledDisplaying[11] = true;
                                                }
                                                else{
                                                    for(int i = 0; i < 21; i++){
                                                        if(i == 11){
                                                            continue;
                                                        }
                                                        else{
                                                            if(unfilledDisplaying[i] != true){
                                                                Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                toast.show();
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    elevenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                    unfilledDisplaying[11] = false;
                                                }
                                                return false;
                                            }
                                        }
        );

        ImageView twelvePeople = findViewById(R.id.btn12);
        twelvePeople.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if(unfilledDisplaying[12] == false){
                                                    twelvePeople.setImageResource(R.drawable.drop_down_bar_small);
                                                    unfilledDisplaying[6] = true;
                                                }
                                                else{
                                                    for(int i = 0; i < 21; i++){
                                                        if(i == 12){
                                                            continue;
                                                        }
                                                        else{
                                                            if(unfilledDisplaying[i] != true){
                                                                Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                toast.show();
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    twelvePeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                    unfilledDisplaying[12] = false;
                                                }
                                                return false;
                                            }
                                        }
        );

        ImageView thirteenPeople = findViewById(R.id.btn13);
        thirteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View v, MotionEvent event) {
                                                  if(unfilledDisplaying[13] == false){
                                                      thirteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                      unfilledDisplaying[13] = true;
                                                  }
                                                  else{
                                                      for(int i = 0; i < 21; i++){
                                                          if(i == 13){
                                                              continue;
                                                          }
                                                          else{
                                                              if(unfilledDisplaying[i] != true){
                                                                  Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                  toast.show();
                                                                  return false;
                                                              }
                                                          }
                                                      }
                                                      thirteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                      unfilledDisplaying[13] = false;
                                                  }
                                                  return false;
                                              }
                                          }
        );

        ImageView fourteenPeople = findViewById(R.id.btn14);
        fourteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View v, MotionEvent event) {
                                                  if(unfilledDisplaying[14] == false){
                                                      fourteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                      unfilledDisplaying[14] = true;
                                                  }
                                                  else{
                                                      for(int i = 0; i < 21; i++){
                                                          if(i == 14){
                                                              continue;
                                                          }
                                                          else{
                                                              if(unfilledDisplaying[i] != true){
                                                                  Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                  toast.show();
                                                                  return false;
                                                              }
                                                          }
                                                      }
                                                      fourteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                      unfilledDisplaying[14] = false;
                                                  }
                                                  return false;
                                              }
                                          }
        );

        ImageView fifteenPeople = findViewById(R.id.btn15);
        fifteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                             @Override
                                             public boolean onTouch(View v, MotionEvent event) {
                                                 if(unfilledDisplaying[15] == false){
                                                     fifteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                     unfilledDisplaying[15] = true;
                                                 }
                                                 else{
                                                     for(int i = 0; i < 21; i++){
                                                         if(i == 15){
                                                             continue;
                                                         }
                                                         else{
                                                             if(unfilledDisplaying[i] != true){
                                                                 Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                 toast.show();
                                                                 return false;
                                                             }
                                                         }
                                                     }
                                                     fifteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                     unfilledDisplaying[15] = false;
                                                 }
                                                 return false;
                                             }
                                         }
        );

        ImageView sixteenPeople = findViewById(R.id.btn16);
        sixteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                             @Override
                                             public boolean onTouch(View v, MotionEvent event) {
                                                 if(unfilledDisplaying[16] == false){
                                                     sixteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                     unfilledDisplaying[16] = true;
                                                 }
                                                 else{
                                                     for(int i = 0; i < 21; i++){
                                                         if(i == 16){
                                                             continue;
                                                         }
                                                         else{
                                                             if(unfilledDisplaying[i] != true){
                                                                 Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                 toast.show();
                                                                 return false;
                                                             }
                                                         }
                                                     }
                                                     sixteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                     unfilledDisplaying[16] = false;
                                                 }
                                                 return false;
                                             }
                                         }
        );

        ImageView seventeenPeople = findViewById(R.id.btn17);
        seventeenPeople.setOnTouchListener(new View.OnTouchListener() {
                                               @Override
                                               public boolean onTouch(View v, MotionEvent event) {
                                                   if(unfilledDisplaying[17] == false){
                                                       seventeenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                       unfilledDisplaying[17] = true;
                                                   }
                                                   else{
                                                       for(int i = 0; i < 21; i++){
                                                           if(i == 17){
                                                               continue;
                                                           }
                                                           else{
                                                               if(unfilledDisplaying[i] != true){
                                                                   Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                   toast.show();
                                                                   return false;
                                                               }
                                                           }
                                                       }
                                                       seventeenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                       unfilledDisplaying[17] = false;
                                                   }
                                                   return false;
                                               }
                                           }
        );

        ImageView eighteenPeople = findViewById(R.id.btn18);
        eighteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View v, MotionEvent event) {
                                                  if(unfilledDisplaying[18] == false){
                                                      eighteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                      unfilledDisplaying[18] = true;
                                                  }
                                                  else{
                                                      for(int i = 0; i < 21; i++){
                                                          if(i == 18){
                                                              continue;
                                                          }
                                                          else{
                                                              if(unfilledDisplaying[i] != true){
                                                                  Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                  toast.show();
                                                                  return false;
                                                              }
                                                          }
                                                      }
                                                      eighteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                      unfilledDisplaying[18] = false;
                                                  }
                                                  return false;
                                              }
                                          }
        );

        ImageView ninteenPeople = findViewById(R.id.btn19);
        ninteenPeople.setOnTouchListener(new View.OnTouchListener() {
                                             @Override
                                             public boolean onTouch(View v, MotionEvent event) {
                                                 if(unfilledDisplaying[19] == false){
                                                     eighteenPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                     unfilledDisplaying[19] = true;
                                                 }
                                                 else{
                                                     for(int i = 0; i < 21; i++){
                                                         if(i == 19){
                                                             continue;
                                                         }
                                                         else{
                                                             if(unfilledDisplaying[i] != true){
                                                                 Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                 toast.show();
                                                                 return false;
                                                             }
                                                         }
                                                     }
                                                     ninteenPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                     unfilledDisplaying[19] = false;
                                                 }
                                                 return false;
                                             }
                                         }
        );

        ImageView twentyPeople = findViewById(R.id.btn20);
        twentyPeople.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if(unfilledDisplaying[20] == false){
                                                    twentyPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                    unfilledDisplaying[20] = true;
                                                }
                                                else{
                                                    for(int i = 0; i < 21; i++){
                                                        if(i == 20){
                                                            continue;
                                                        }
                                                        else{
                                                            if(unfilledDisplaying[i] != true){
                                                                Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                toast.show();
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    twentyPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                    unfilledDisplaying[20] = false;
                                                }
                                                return false;
                                            }
                                        }
        );

        ImageView buyoutPeople = findViewById(R.id.btnBuyout);
        buyoutPeople.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if(unfilledDisplaying[20] == false){
                                                    twentyPeople.setImageResource(R.drawable.drop_down_bar_small);
                                                    unfilledDisplaying[20] = true;
                                                }
                                                else{
                                                    for(int i = 0; i < 21; i++){
                                                        if(i == 20){
                                                            continue;
                                                        }
                                                        else{
                                                            if(unfilledDisplaying[i] != true){
                                                                Toast toast=Toast.makeText(getApplicationContext(),"Please Deselect the "+i+" Guest Option to Continue",Toast.LENGTH_SHORT);
                                                                toast.show();
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    twentyPeople.setImageResource(R.drawable.drop_down_bar_small_filled);
                                                    unfilledDisplaying[20] = false;
                                                }
                                                return false;
                                            }
                                        }
        );



    }

    private String guestNumber(){
        for(int i = 0; i< 21; i++){
            if(unfilledDisplaying[i] == false){
                return Integer.toString(i);
            }
        }
        return "0";
    }
}