package com.hank.security.springbootsecurity.service;

import com.hank.security.springbootsecurity.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;

public interface BankService {
@PreAuthorize("isAnonymous()")  //允许匿名访问
public Account readAccount(Long id);
@PreAuthorize("isAnonymous()")  //允许匿名访问
public Account[] findAccounts();
@PreAuthorize("hasAuthority('p_transfer') and hasAuthority('p_read_account')")  //含有对应资源权限才可以访问
public Account post(Account account, double amount);
}