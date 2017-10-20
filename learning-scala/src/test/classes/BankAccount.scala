package test.classes

class BankAccount(initialBalance: Double) {
  private var balance = initialBalance
  def currentBalance = balance
  def deposit(amount: Double) = {balance += amount;balance}
  def withdraw(amount: Double) = {balance-=amount;balance}
}

class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
  private val checkingExpenses = 1.0
  override def deposit(amount: Double): Double =  super.deposit(amount-checkingExpenses)
  override def withdraw(amount: Double): Double = super.withdraw(amount+checkingExpenses)
}