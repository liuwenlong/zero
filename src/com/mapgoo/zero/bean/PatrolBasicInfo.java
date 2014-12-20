package com.mapgoo.zero.bean;

import java.io.Serializable;

public class PatrolBasicInfo  implements Serializable {

	public boolean ConduitSafety;
	public String Hypotension;
	public String Hypertension;
	public boolean AroundHygiene;
	public boolean ResidentialHygiene;
	public boolean Thinking;
	public boolean GarbageCollection;
	public boolean Indoor;
	public boolean Emotion;
	public boolean Outdoor;
	public boolean SmellHygiene;
	public boolean Eating;
	public boolean Memory;
	public boolean Neighborhood;
	public boolean Action;
	public boolean Medical;
	public boolean PersonalHygiene;
	public String AlldayTel;
	public boolean Pressure;
	public boolean DressHygiene;
	public boolean CircuitSafety;
	public boolean Disease;
	public boolean PatrolStatus;
	public boolean ApplianceSafety;
	public boolean HousesDamaged;
	public boolean CoalGasSafety;
	public boolean CoalFireSafety;
	public boolean ListeningExpress;
	public boolean Autism;
	public String RelativeTel;
	public String FamilySafety;
	public String MonitorTel;
	public String SafetyImg1;
	public String SafetyImg4;
	public String SafetyImg3;
	public String SafetyImg2;
	public int HumanID;
	
	public void setHumanID(int str){
		HumanID = str;
	}		
	public void setSafetyImg2(String str){
		SafetyImg2 = str;
	}		
	public void setSafetyImg3(String str){
		SafetyImg3 = str;
	}	
	public void setSafetyImg4(String str){
		SafetyImg4 = str;
	}		
	public void setSafetyImg1(String str){
		SafetyImg1 = str;
	}			
	public void setMonitorTel(String str){
		MonitorTel = str;
	}			
	public void setFamilySafety(String str){
		FamilySafety = str;
	}		
	public void setRelativeTel(String str){
		RelativeTel = str;
	}		
	public void setAutism(Boolean str){
		Autism = str;
	}		
	public void setListeningExpress(Boolean str){
		ListeningExpress = str;
	}		
	public void setCoalFireSafety(Boolean str){
		CoalFireSafety = str;
	}		
	public void setCoalGasSafety(Boolean str){
		CoalGasSafety = str;
	}			
	public void setHousesDamaged(Boolean str){
		HousesDamaged = str;
	}		
	public void setApplianceSafety(Boolean str){
		ApplianceSafety = str;
	}		
	public void setPatrolStatus(Boolean str){
		PatrolStatus = str;
	}			
	public void setDisease(Boolean str){
		Disease = str;
	}		
	public void setCircuitSafety(Boolean str){
		CircuitSafety = str;
	}			
	public void setDressHygiene(Boolean str){
		DressHygiene = str;
	}		
	public void setPressure(Boolean str){
		Pressure = str;
	}		
	public void setAlldayTel(String str){
		AlldayTel = str;
	}		
	
	public void setPersonalHygiene(Boolean str){
		PersonalHygiene = str;
	}	
	public void setMedical(Boolean str){
		Action = str;
	}		
	public void setAction(Boolean str){
		Action = str;
	}	
	public void setNeighborhood(Boolean str){
		Neighborhood = str;
	}			
	public void setMemory(Boolean str){
		Memory = str;
	}			
	public void setEating(Boolean str){
		Eating = str;
	}			
	public void setSmellHygiene(Boolean str){
		SmellHygiene = str;
	}		
	public void setOutdoor(Boolean str){
		Outdoor = str;
	}		
	public void setEmotion(Boolean str){
		Emotion = str;
	}		
	public void setIndoor(Boolean str){
		Indoor = str;
	}		
	public void setGarbageCollection(Boolean str){
		GarbageCollection = str;
	}			
	public void setThinking(Boolean str){
		Thinking = str;
	}		
	public void setResidentialHygiene(Boolean str){
		ResidentialHygiene = str;
	}	
	public void setAroundHygiene(Boolean str){
		AroundHygiene = str;
	}	
	public void setHypertension(String str){
		Hypertension = str;
	}
	public void setHypotension(String str){
		Hypotension = str;
	}
	public void setImagePath(Boolean str){
		ConduitSafety = str;
	}
	
	
	public void setBoolean(Boolean b,int num){
		switch (num) {
		case 0:ListeningExpress = b; break;
		case 1:Eating = b; break;
		case 2:Action = b; break;
		case 3:Medical = b; break;
		case 4:Disease = b; break;
		case 5:PersonalHygiene = b; break;
		case 6:DressHygiene = b; break;
		case 7:SmellHygiene = b; break;
		case 8:AroundHygiene = b; break;
		case 9:ResidentialHygiene = b; break;
		case 10:Indoor = b; break;
		case 11:Outdoor = b; break;
		case 12:Neighborhood = b; break;
		case 13:GarbageCollection = b; break;
		case 14:HousesDamaged = b; break;
		case 15:CoalGasSafety = b; break;
		case 16:CoalFireSafety = b; break;
		case 17:ConduitSafety = b; break;
		case 18:ApplianceSafety = b; break;
		case 19:CircuitSafety = b; break;
		case 20:Autism = b; break;
		case 21:Memory = b; break;
		case 22:Emotion = b; break;
		case 23:Thinking = b; break;
		case 24:Pressure = b; break;
		default:
			break;
		}
	}
	public boolean getBoolean(int num){
		boolean b =false;
		switch (num) {
		case 0:b=ListeningExpress ; break;
		case 1:b=Eating ; break;
		case 2:b=Action ; break;
		case 3:b=Medical ; break;
		case 4:b=Disease ; break;
		case 5:b=PersonalHygiene ; break;
		case 6:b=DressHygiene ; break;
		case 7:b=SmellHygiene ; break;
		case 8:b=AroundHygiene ; break;
		case 9:b=ResidentialHygiene ; break;
		case 10:b=Indoor ; break;
		case 11:b=Outdoor ; break;
		case 12:b=Neighborhood ; break;
		case 13:b=GarbageCollection ; break;
		case 14:b=HousesDamaged ; break;
		case 15:b=CoalGasSafety ; break;
		case 16:b=CoalFireSafety ; break;
		case 17:b=ConduitSafety ; break;
		case 18:b=ApplianceSafety ; break;
		case 19:b=CircuitSafety ; break;
		case 20:b=Autism ; break;
		case 21:b=Memory ; break;
		case 22:b=Emotion ; break;
		case 23:b=Thinking ; break;
		case 24:b=Pressure ; break;
		default:
			break;
		}
		
	return b;
	}	
	
	
}
