package com.chinadrtv.erp.model.agent;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "PROVINCE", schema = "ACOAPP_OMS")
@Entity
public class Province implements Serializable {
    private String provinceid;


    @Id
    @javax.persistence.Column(name = "PROVINCEID")
    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    private String code;

    @javax.persistence.Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String continent;

    @javax.persistence.Column(name = "CONTINENT")
    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    private String ally1;

    @javax.persistence.Column(name = "ALLY1")
    public String getAlly1() {
        return ally1;
    }

    public void setAlly1(String ally1) {
        this.ally1 = ally1;
    }

    private String ally2;

    @javax.persistence.Column(name = "ALLY2")
    public String getAlly2() {
        return ally2;
    }

    public void setAlly2(String ally2) {
        this.ally2 = ally2;
    }

    private String ally3;

    @javax.persistence.Column(name = "ALLY3")
    public String getAlly3() {
        return ally3;
    }

    public void setAlly3(String ally3) {
        this.ally3 = ally3;
    }

    private String chinese;

    @javax.persistence.Column(name = "CHINESE")
    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    private String english;

    @javax.persistence.Column(name = "ENGLISH")
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    private String capital;

    @javax.persistence.Column(name = "CAPITAL")
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    private int feezone;

    @javax.persistence.Column(name = "FEEZONE")
    public int getFeezone() {
        return feezone;
    }

    public void setFeezone(int feezone) {
        this.feezone = feezone;
    }

    /** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Province other = (Province) obj;
		if (ally1 == null) {
			if (other.ally1 != null)
				return false;
		} else if (!ally1.equals(other.ally1))
			return false;
		if (ally2 == null) {
			if (other.ally2 != null)
				return false;
		} else if (!ally2.equals(other.ally2))
			return false;
		if (ally3 == null) {
			if (other.ally3 != null)
				return false;
		} else if (!ally3.equals(other.ally3))
			return false;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (chinese == null) {
			if (other.chinese != null)
				return false;
		} else if (!chinese.equals(other.chinese))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (continent == null) {
			if (other.continent != null)
				return false;
		} else if (!continent.equals(other.continent))
			return false;
		if (english == null) {
			if (other.english != null)
				return false;
		} else if (!english.equals(other.english))
			return false;
		if (feezone != other.feezone)
			return false;
		if (provinceid == null) {
			if (other.provinceid != null)
				return false;
		} else if (!provinceid.equals(other.provinceid))
			return false;
		return true;
	}

    /** 
	 * <p>Title: hashCode</p>
	 * <p>Description: 您不能随便重写该方法，该方法很重要</p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ally1 == null) ? 0 : ally1.hashCode());
		result = prime * result + ((ally2 == null) ? 0 : ally2.hashCode());
		result = prime * result + ((ally3 == null) ? 0 : ally3.hashCode());
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((chinese == null) ? 0 : chinese.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((english == null) ? 0 : english.hashCode());
		result = prime * result + feezone;
		result = prime * result + ((provinceid == null) ? 0 : provinceid.hashCode());
		return result;
	}
}
