/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.room.demo.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * The Room database that contains the Users table
 */
@Database(entities = {User.class}, version = 3)
public abstract class UsersDatabase extends RoomDatabase {

    private static volatile UsersDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UsersDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UsersDatabase.class, "Sample.db")
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3) //数据库升级
                            .allowMainThreadQueries() //允许在主线程中操作。默认是不允许的
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            System.out.println("migrate 2222");
            database.execSQL("ALTER TABLE Users "
                    + " ADD COLUMN v2 TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            System.out.println("migrate 3333");
            database.execSQL("ALTER TABLE Users "
                    + " ADD COLUMN v3 TEXT");
        }
    };
}
