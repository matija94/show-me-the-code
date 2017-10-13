package classes

class BankAccount(private var balance: Long) {
  
  def withdraw(amount: Long) {
    if(amount>balance) throw new IllegalStateException("amount cannot be larger than current balance")
    else {
      balance -= amount
    }
  }
  
  def deposit(amount: Long) {balance+=amount}
  
}