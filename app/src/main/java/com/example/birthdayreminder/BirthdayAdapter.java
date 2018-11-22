package com.example.birthdayreminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayItemHolder>{

    List<Birthday> birthdays;
    BirthdayDao birthdayDao;
    public NotifyInterface notifyInterface; //Diese Variable kann nicht nur Interfaces aufnehmen, sondern auch Klassen die dieses Interface implementieren

    public BirthdayAdapter(BirthdayDao birthdayDao, NotifyInterface notifyInterface) {
        this.notifyInterface = notifyInterface;
        this.birthdayDao = birthdayDao;
        this.birthdays = birthdayDao.getAllBirthdays();
    }

    public void updateData() {
        this.birthdays = this.birthdayDao.getAllBirthdays();
        this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.birthdays.remove(position);
        this.notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public BirthdayItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.birthday_item, parent, false);

        return new BirthdayItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayItemHolder holder, int position) {
        Birthday birthday = birthdays.get(position);
        holder.name.setText(birthday.getName());
        holder.birthday.setText(birthday.getDay() + "." + birthday.getMonth() + "." + birthday.getYear());
        holder.birthdayDao = this.birthdayDao;
        holder.itemView.setTag(birthday.id);

        holder.bindView(position, notifyInterface);
    }

    @Override
    public int getItemCount() {
        return birthdays.size();
    }

    public int getDiffYears(Date birth, int y, int m, int d) {
        Calendar a = getCalendar(birth);
        int diff = y - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > m ||
                (a.get(Calendar.MONTH) == m && a.get(Calendar.DATE) > d)) {
            diff--;
        }
        return -diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTime(date);
        return cal;
    }

    interface NotifyInterface { //Dieses Interface wird hier erstellt, weil es als externes Interface keinen Sinn machen würde. Es ist ein "nested" Interface und wird über Klassenname.Interface implementiert.
        void notifyEvent(int position);
    }
}
