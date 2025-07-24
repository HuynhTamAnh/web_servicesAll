package com.example.ss7.service;

import com.example.ss7.entity.Harvest;
import com.example.ss7.entity.PaymentSlip;
import com.example.ss7.entity.Seed;
import com.example.ss7.entity.Worker;
import com.example.ss7.repository.HarvestRepository;
import com.example.ss7.repository.PaymentSlipRepository;
import com.example.ss7.repository.SeedRepository;
import com.example.ss7.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticalService {

    @Autowired
    private SeedRepository seedRepository;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private PaymentSlipRepository paymentSlipRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public int countRemainingSeeds() {
        List<Seed> seeds = seedRepository.findAll();
        return seeds.stream().mapToInt(Seed::getQuantity).sum();
    }

    public double totalHarvestMoneyThisMonth() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        List<Harvest> harvests = harvestRepository.findAll();
        return harvests.stream()
                .filter(harvest -> harvest.getCreatedAt().isAfter(startOfMonth) &&
                        harvest.getCreatedAt().isBefore(endOfMonth))
                .mapToDouble(Harvest::getTotalMoney)
                .sum();
    }

    public double totalPaymentSlipsThisMonth() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        List<PaymentSlip> paymentSlips = paymentSlipRepository.findAll();
        return paymentSlips.stream()
                .filter(slip -> slip.getCreatedAt().isAfter(startOfMonth) &&
                        slip.getCreatedAt().isBefore(endOfMonth))
                .mapToDouble(PaymentSlip::getMoney)
                .sum();
    }

    public Map<String, Double> profitLossOverYear() {
        Map<String, Double> profitLoss = new HashMap<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear(), month);
            LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

            // Tính tổng thu từ harvest
            double totalIncome = harvestRepository.findAll().stream()
                    .filter(harvest -> harvest.getCreatedAt().isAfter(startOfMonth) &&
                            harvest.getCreatedAt().isBefore(endOfMonth))
                    .mapToDouble(Harvest::getTotalMoney)
                    .sum();

            // Tính tổng chi từ payment slips
            double totalExpense = paymentSlipRepository.findAll().stream()
                    .filter(slip -> slip.getCreatedAt().isAfter(startOfMonth) &&
                            slip.getCreatedAt().isBefore(endOfMonth))
                    .mapToDouble(PaymentSlip::getMoney)
                    .sum();

            // Tính lương công nhân (giả sử trả lương hàng tháng)
            double totalSalary = workerRepository.findAll().stream()
                    .mapToDouble(Worker::getSalary)
                    .sum();

            double profit = totalIncome - totalExpense - totalSalary;
            profitLoss.put("Tháng " + month, profit);
        }

        return profitLoss;
    }

    public double totalWorkerSalaryThisMonth() {
        List<Worker> workers = workerRepository.findAll();
        return workers.stream()
                .mapToDouble(Worker::getSalary)
                .sum();
    }
}
