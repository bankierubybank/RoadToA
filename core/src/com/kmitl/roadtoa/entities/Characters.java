package com.kmitl.roadtoa.entities;

public class Characters {
	
	private int maxHP, currentHP, attackPower;

	//Constructor
	public Characters(int maxHP, int attackPower){
		this.maxHP = maxHP;
		currentHP = this.maxHP;
		this.attackPower = attackPower;
	}
	
	public void setCurrentHP(int currentHP){
		this.currentHP = currentHP;
	}
	
	public void setMaxHP(int maxHP){
		this.maxHP = maxHP;
	}
	
	public void setAttackPower(int attackPower){
		this.attackPower = attackPower;
	}
	
	public int getCurrentHP(){
		return currentHP;
	}
	
	public int getMaxHP(){
		return maxHP;
	}
	
	public int getAttackPower(){
		return attackPower;
	}
	
	public void damaged(int damage) {
		this.setCurrentHP(this.currentHP - damage);
	}
	
	public void heal(int amount){
		if (this.getCurrentHP() + amount > this.getMaxHP()) {
			this.setCurrentHP(this.getMaxHP());
		}
		else {
			this.setCurrentHP(this.getCurrentHP() + amount);
		}
	}

}
