package com.example;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Controller
public class AccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@Value("${spring.datasource.url}")
	private String dbUrl;

//	@Autowired
	private DataSource dataSource;
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

//	@RequestMapping("/account")
//	public String home(Model model) {
//		return "home";
//	}

	@RequestMapping("/createaccountform")
	public String createAccountForm(Model model) {
		model.addAttribute("account", new Account());
		LOGGER.info("Navigated to {}.", "Create Account");
		return "createaccount";
	}

	@RequestMapping("/accounts")
	public String accounts(Model model) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = getDataSource().getConnection();
			stmt = connection.createStatement();
			String sql;
			sql = "SELECT id, sfid, name, phone, ownership FROM salesforce.account";
			rs = stmt.executeQuery(sql);
			List<Account> accounts = new ArrayList<>();
			// Extract data from result set
			while (rs.next()) {
				long id = rs.getInt("id");
				String sfid = rs.getString("sfid");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String ownership = rs.getString("ownership");
				accounts.add(new Account(id, sfid, name, phone, ownership));
			}
			model.addAttribute("accounts", accounts);
			LOGGER.info("Fetched all {}.", "Accounts");
			return "accounts";
		} catch (Exception e) {
			return e.toString();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createAccount(@ModelAttribute Account account, Model model) throws SQLException {
//		long id = account.getId();
		String name = account.getName();
		String phone = account.getPhone();
		String ownership = account.getOwnership();
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = getDataSource().getConnection();
			stmt = connection.createStatement();
			String sql;
			sql = "insert into salesforce.account(name, phone, ownership) values " + "('" + name + "', '" + phone
					+ " ',' " + ownership + "');";
			System.out.println(sql);
			int result = stmt.executeUpdate(sql);
			LOGGER.info("Created {} of {}(s)", result, "Account");
			model.addAttribute("account", account);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("Exception", e.toString());
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}
		return "result";
//		return "accounts";
	}
	
	@RequestMapping(value = "/updateaccountform/{id}", method = RequestMethod.GET)
	public String updateAccountForm(Model model, @PathVariable(required = true, name = "id") Long id) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = getDataSource().getConnection();
			stmt = connection.createStatement();
			String sql;
			sql = "SELECT id, sfid, name, phone, ownership FROM salesforce.account where id="+id;
			rs = stmt.executeQuery(sql);
			Account account = null;
			// Extract data from result set
			if (rs.next()) {
				String sfid = rs.getString("sfid");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String ownership = rs.getString("ownership");
				account = new Account(id, sfid, name, phone, ownership);
			}
			model.addAttribute("account", account);
			LOGGER.info("Fetched {} with id {}.", "Account", id);
		} catch (Exception e) {
			return e.toString();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}
		LOGGER.info("Navigated to {}.", "Update Account");
		return "updateaccount";
	}

	@RequestMapping(value = "/updateaccount", method = RequestMethod.POST)
	public String updateAccount(@ModelAttribute Account account, Model model) throws SQLException {
		long id = account.getId();
		String name = account.getName();
		String phone = account.getPhone();
		String ownership = account.getOwnership();
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = getDataSource().getConnection();
			stmt = connection.createStatement();
			String sql;
			sql = "update salesforce.account set name = '"+name+"', phone = '"+phone+"', ownership = '"+ownership+"' where id="+id;
			System.out.println(sql);
			int result = stmt.executeUpdate(sql);
			LOGGER.info("Updated {} record of {} having Id: {}", result, "Account", id);
			model.addAttribute("account", account);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("Exception", e.toString());
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}
		return "result";
//		return "accounts";
	}
	
	@RequestMapping(value = "/deleteaccount/{id}", method = RequestMethod.GET)
	public String deleteAccountForm(Model model, @PathVariable(required = true, name = "id") Long id) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = getDataSource().getConnection();
			stmt = connection.createStatement();
			String sql = "DELETE FROM salesforce.account where id="+id;
			stmt.executeUpdate(sql);
			LOGGER.info("Deleted {} with id {}.", "Account", id);
		} catch (Exception e) {
			return e.toString();
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}
		LOGGER.info("Navigated to {}.", "Update Account");
		return accounts(model);
	}

	@Bean
	public DataSource getDataSource() throws SQLException, URISyntaxException {
		System.out.println("dbUrl: "+dbUrl);
		if (dataSource == null) {
			if (dbUrl == null || dbUrl.isEmpty()) {
				dataSource = new HikariDataSource();
			} else {
				HikariConfig config = new HikariConfig();
				
				URI dbUri = new URI(dbUrl);
				String username = dbUri.getUserInfo().split(":")[0];
			    String password = dbUri.getUserInfo().split(":")[1];
			    String newDbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
				
				config.setJdbcUrl(newDbUrl);
				config.setUsername(username);
				config.setPassword(password);
				dataSource = new HikariDataSource(config);
			}
		}
		return dataSource;
	}
}
