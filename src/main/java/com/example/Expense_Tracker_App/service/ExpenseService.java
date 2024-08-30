package com.example.Expense_Tracker_App.service;

import com.example.Expense_Tracker_App.model.Expense;
import com.example.Expense_Tracker_App.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public void saveExpense(Expense expense){
        expenseRepository.save(expense);
    }

    public Expense getExpencseById(Long id){
        return expenseRepository.findById(id).orElse(null);
    }

    public void deletebyId(Long id){
        expenseRepository.deleteById(id);
    }
}
