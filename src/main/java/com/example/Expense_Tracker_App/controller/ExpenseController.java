package com.example.Expense_Tracker_App.controller;

import com.example.Expense_Tracker_App.model.Expense;
import com.example.Expense_Tracker_App.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Expense> expenses = expenseService.getAllExpenses();
        BigDecimal totalAmount  = expenses.stream().map(Expense::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        return "index";
    }

    @GetMapping("/addExpense")
    public String showAddExpensePage(Model model){
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "add-expense";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense, Model model){
        expenseService.saveExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/editExpense/{id}")
    public String showUpdateExpensePage(@PathVariable("id") Long id, Model model){
        Expense expense = expenseService.getExpencseById(id);
        model.addAttribute("expense", expense);
        return "update-expense";
    }

    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") Long id,@ModelAttribute("expense") Expense expense){
        Expense existingExpense = expenseService.getExpencseById(id);
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setAmount(expense.getAmount());
        expenseService.saveExpense(existingExpense);
        return "redirect:/";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable("id") Long id){
        expenseService.deletebyId(id);
        return "redirect:/";
    }
}
