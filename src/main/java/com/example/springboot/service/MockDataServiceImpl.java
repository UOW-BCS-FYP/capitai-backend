package com.example.springboot.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.BudgetCtgyRepository;
import com.example.springboot.dao.ExpIncRepository;
import com.example.springboot.dao.GoalRepository;
import com.example.springboot.dao.IncomeAndSpendingInfoRepository;
import com.example.springboot.dao.IncomeRepository;
import com.example.springboot.dao.MessageRepository;
import com.example.springboot.dao.NotificationRepository;
import com.example.springboot.dao.SpendingRepository;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.IncomeAndSpendingInfo;
import com.example.springboot.model.IncomeInfo;
import com.example.springboot.model.MessageInfo;
import com.example.springboot.model.NotificationInfo;
import com.example.springboot.model.SpendingInfo;
import com.example.springboot.model.UserInfo;

import jakarta.transaction.Transactional;

@Service
public class MockDataServiceImpl implements MockDataService {
    
    @Autowired
    BudgetCtgyRepository budgetRepo;
    @Autowired
    ExpIncRepository expIncRepo;
    @Autowired
    SpendingRepository spendingRepo;
    @Autowired
    IncomeRepository incomeRepo;
    @Autowired
    MessageRepository msgRepo;
    @Autowired
    GoalRepository goalRepo;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    IncomeAndSpendingInfoRepository incomeAndSpendingInfoRepository;

    @Override
    public void generateAllData(UserInfo userInfo) {
        Date now = Date.from(Instant.now());
        try {
            
                
            BudgetCtgyInfo cat1 = new BudgetCtgyInfo(1, "Furniture repairment", 350, false, 0, now, true, userInfo);
            BudgetCtgyInfo cat2 = new BudgetCtgyInfo(2, "Rent", 10000, true, 1, now, true, userInfo);
            BudgetCtgyInfo cat3 = new BudgetCtgyInfo(3, "Transportation", 200, true, 1, now, true, userInfo);
            BudgetCtgyInfo cat4 = new BudgetCtgyInfo(4, "Tuition fee", 10000, false, 0, now, true, userInfo);
            BudgetCtgyInfo cat5 = new BudgetCtgyInfo(5, "Food", 3000, true, 1, now, true, userInfo);
            BudgetCtgyInfo cat6 = new BudgetCtgyInfo(6, "Water bill", 300, true, 3, now, false, userInfo);
            BudgetCtgyInfo cat7 = new BudgetCtgyInfo(7, "Other", 500, false, 0, now, true, userInfo);
            BudgetCtgyInfo cat8 = new BudgetCtgyInfo(8, "Other", 600, false, 0, now, true, userInfo);
            budgetRepo.saveAll(Arrays.asList(
                cat1,
                cat2,
                cat3,
                cat4,
                cat5,
                cat6,
                cat7,
                cat8
            ));
            
            ExpIncInfo exp1 = new ExpIncInfo(1, "Wage", 10000, true, 1, now, true, userInfo);
            ExpIncInfo exp2 = new ExpIncInfo(2, "Stocks", 1000, true, 1, now, true, userInfo);
            ExpIncInfo exp3 = new ExpIncInfo(3, "Red envelop money", 2500, false, 0, now, true, userInfo);
            ExpIncInfo exp4 = new ExpIncInfo(4, "Business", 25000, true, 3, now, false, userInfo);
            ExpIncInfo exp5 = new ExpIncInfo(5, "Property leasing", 15000, true, 1, now, true, userInfo);
            ExpIncInfo exp6 = new ExpIncInfo(6, "Bitcoin cash out", 5500, false, 0, now, true, userInfo);
            expIncRepo.saveAll(Arrays.asList(
                exp1,
                exp2,
                exp3,
                exp4,
                exp5,
                exp6
            ));
            
            SpendingInfo spending1 = new SpendingInfo(1, "Refridgerator repairment", 350, now, "Technician", cat1, userInfo);
            SpendingInfo spending2 = new SpendingInfo(2, "Rent", 10000, now, "Landlord", cat2, userInfo);
            SpendingInfo spending3 = new SpendingInfo(3, "Taxi", 100, now, "Driver", cat3, userInfo);
            SpendingInfo spending4 = new SpendingInfo(4, "Semester A tuition fee", 10000, now, "University", cat4, userInfo);
            SpendingInfo spending5 = new SpendingInfo(5, "Lunch", 60, now, "Restaurant", cat5, userInfo);
            SpendingInfo spending6 = new SpendingInfo(6, "Water bill", 300, now, "Water department", cat6, userInfo);
            SpendingInfo spending7 = new SpendingInfo(7, "Gift", 350, now, "Giftshop", cat7, userInfo);
            SpendingInfo spending8 = new SpendingInfo(8, "Doctor visit", 350, now, "Clinic", cat8, userInfo);
            spendingRepo.saveAll(Arrays.asList(
                spending1,
                spending2,
                spending3,
                spending4,
                spending5,
                spending6,
                spending7,
                spending8
            ));

            IncomeInfo income1 = new IncomeInfo(1, "Bonds", 5000, now, "Bank", exp2, userInfo);
            IncomeInfo income2 = new IncomeInfo(2, "Website advertisement revenue", 10000, now, "Google", exp4, userInfo);
            IncomeInfo income3 = new IncomeInfo(3, "Bonus", 15000, now, "Boss", exp1, userInfo);
            IncomeInfo income4 = new IncomeInfo(4, "Stock exchange", 20000, now, "Bank", exp2, userInfo);
            IncomeInfo income5 = new IncomeInfo(5, "Day job", 25000, now, "Boss", exp2, userInfo);
            IncomeInfo income6 = new IncomeInfo(6, "Property income", 30000, now, "Renter", exp2, userInfo);
            incomeRepo.saveAll(Arrays.asList(
                income1,
                income2,
                income3,
                income4,
                income5,
                income6
            ));

            MessageInfo msg1 = new MessageInfo(1, "Hello carson", now, "carson", "user", userInfo);
            MessageInfo msg2 = new MessageInfo(2, "Hello user", now, "carson", "ai", userInfo);
            MessageInfo msg3 = new MessageInfo(3, "Hello sunny", now, "sunny", "user", userInfo);
            MessageInfo msg4 = new MessageInfo(4, "Hello user", now, "sunny", "ai", userInfo);
            msgRepo.saveAll(Arrays.asList(
                msg1,
                msg2,
                msg3,
                msg4
            ));

            GoalInfo goal1 = new GoalInfo(1, "Capital Building", "capital building", 10232, 1, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-12"), 0, 0, 0, 0, null, 0, 0, 0, null, userInfo);
            GoalInfo goal2 = new GoalInfo(2, "Debt Payment", "debt payment", 5888, 2, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-1"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal3 = new GoalInfo(3, "Long Term Expense", "long term expense", 2322, 3, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-14"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal4 = new GoalInfo(4, "Capital Building", "capital building", 10930, 4, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-17"), 0, 0, 0, 0, null, 0, 0, 0, null, userInfo);
            GoalInfo goal5 = new GoalInfo(5, "Debt Payment", "debt payment", 5050, 5, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-08-21"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal6 = new GoalInfo(6, "Long Term Expense", "long term expense", 2011, 6, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-07-16"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal7 = new GoalInfo(7, "Capital Building", "capital building", 10249, 7, false, new SimpleDateFormat("yyyy-MM-dd").parse("2024-06-11"), 0, 0, 0, 0, null, 0, 0, 0, null, userInfo);
            GoalInfo goal8 = new GoalInfo(8, "Debt Payment", "debt payment", 4235, 8, false, new SimpleDateFormat("yyyy-MM-dd").parse("2024-05-09"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal9 = new GoalInfo(9, "Long Term Expense", "long term expense", 1899, 9, false, new SimpleDateFormat("yyyy-MM-dd").parse("2024-04-25"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal10 = new GoalInfo(10, "Capital Building", "capital building", 9988, 10, false, new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-22"), 0, 0, 0, 0, null, 0, 0, 0, null, userInfo);
            GoalInfo goal11 = new GoalInfo(11, "Debt Payment", "debt payment", 4679, 11, false, new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-18"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            GoalInfo goal12 = new GoalInfo(12, "Long Term Expense", "long term expense", 1689, 12, false, new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-19"), 0, 0, 0, 0, now, 0, 0, 0, null, userInfo);
            goalRepo.saveAll(Arrays.asList(
                goal1,
                goal2,
                goal3,
                goal4,
                goal5,
                goal6,
                goal7,
                goal8,
                goal9,
                goal10,
                goal11,
                goal12
            ));

            NotificationInfo notif1 = new NotificationInfo(1, "Lunch time is now, warm reminder to record food spending today.", "Click to open I&S page", "spending", "/smart-budgeting/income-n-spending", userInfo);
            NotificationInfo notif2 = new NotificationInfo(2, "A month has passed, warm reminder to review your budget plan.", "Click to open Budget Plan page", "endOfMonth", "/smart-budgeting", userInfo);
            NotificationInfo notif3 = new NotificationInfo(3, "You have enough money for goal Tuition Fee, Congratutations!", "Click to open Goal Tracker page", "goalReached", "/goal-tracker/stat", userInfo);
            NotificationInfo notif4 = new NotificationInfo(4, "Good morning, warm reminder to record your commute spending.", "Click to open I&S page", "commute", "/smart-budgeting/income-n-spending", userInfo);
            NotificationInfo notif5 = new NotificationInfo(5, "Dinner time is now, warm reminder to record food spending today.", "Click to open I&S page", "spending", "/smart-budgeting/income-n-spending", userInfo);
            NotificationInfo notif6 = new NotificationInfo(6, "You have enough money for goal Capital Building, Congratutations!", "Click to open Goal Tracker page", "goalReached", "/goal-tracker/stat", userInfo);
            NotificationInfo notif7 = new NotificationInfo(7, "Good evening, warm reminder to record your commute spending.", "Click to open I&S page", "commute", "/smart-budgeting/income-n-spending", userInfo);
            notificationRepository.saveAll(Arrays.asList(
                notif1,
                notif2,
                notif3,
                notif4,
                notif5,
                notif6,
                notif7
            ));

            IncomeAndSpendingInfo ins1 = new IncomeAndSpendingInfo(1, "Rent Payment", 10000, now, "landlord", false, exp4, cat2, userInfo);
            IncomeAndSpendingInfo ins2 = new IncomeAndSpendingInfo(2, "MTR", 10, now, "MTR", false, exp3, cat3, userInfo);
            IncomeAndSpendingInfo ins3 = new IncomeAndSpendingInfo(3, "lunch", 50, now, "ABC restaurant", false, exp3, cat5, userInfo);
            IncomeAndSpendingInfo ins4 = new IncomeAndSpendingInfo(4, "water bill payment", 300, now, "Water Supplies Department", false, exp3, cat6, userInfo);
            IncomeAndSpendingInfo ins5 = new IncomeAndSpendingInfo(5, "wage", 10000, now, "manager", true, exp1, cat1, userInfo);
            IncomeAndSpendingInfo ins6 = new IncomeAndSpendingInfo(6, "stock exchange", 10000, now, "bank", true, exp2, cat2, userInfo);
            IncomeAndSpendingInfo ins7 = new IncomeAndSpendingInfo(7, "website advertisement revenue", 10000, now, "google", true, exp4, cat7, userInfo);
            incomeAndSpendingInfoRepository.saveAll(Arrays.asList(
                ins1,
                ins2,
                ins3,
                ins4,
                ins5,
                ins6,
                ins7
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteAllData(UserInfo userInfo) {
        // TODO Auto-generated method stub
        incomeAndSpendingInfoRepository.deleteAllByUserInfo(userInfo);
        notificationRepository.deleteAllByUserInfo(userInfo);
        spendingRepo.deleteAllByUserInfo(userInfo);
        goalRepo.deleteAllByUserInfo(userInfo);
        incomeRepo.deleteAllByUserInfo(userInfo);
        budgetRepo.deleteAllByUserInfo(userInfo);
        msgRepo.deleteAllByUserInfo(userInfo);
        expIncRepo.deleteAllByUserInfo(userInfo);
    }
}
