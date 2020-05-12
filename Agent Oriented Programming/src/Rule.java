/*
 * by Gerardo Ayala
 * Universidad de las Américas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Vector;

//To be defined for each specific knowledge base
public class Rule implements Comparable
{
    int number;
    Vector<Condition> conditions;
    Vector<Commitment> commitments;
    int priority;
    //-------------------

    public Rule(int aNumber,
                Vector<Condition> aConditionList,
                Vector<Commitment> aCommitmentList)
    {
        number = aNumber;
        conditions = aConditionList;
        commitments = aCommitmentList;
        priority = 0;
    }//end Rule


    public void setPriority(int unaPrioridad)
    {
        priority = unaPrioridad;
    }//end setPriority

    public int compareTo(Object unObjeto)
    {
        Rule anotherRule;
        //--------------
        anotherRule = (Rule) unObjeto;
        if(this.priority > anotherRule.priority)
            return 1;
            //end if
        else
        {
            if(this.priority == anotherRule.priority)
                return 0;
                //end if
            else
                return -1;
            //end else
        }//end else
    }//end compareTo


    public boolean equals(Rule aRule)
    {
        boolean isEqual;
        int i,j;
        boolean predicateNotFound;
        String condition;
        String aRuleCondition;
        //--------------

        isEqual = true;

        if(
                (conditions.size()!=aRule.conditions.size()) ||
                        (commitments.size()!=aRule.commitments.size())
                )
            return false;
            //end if
        else
        {
            i=0;
            while(i<conditions.size())
            {
                predicateNotFound = true;
                j = 0;
                while(j < aRule.conditions.size())
                {
                    condition = conditions.get(i).conditionPredicate;
                    aRuleCondition = aRule.conditions.get(j).conditionPredicate;
                    if(condition.equals(aRuleCondition))
                    {
                        predicateNotFound = false;
                    }//end if
                    j = j + 1;
                }//end while
                if(predicateNotFound)
                    isEqual = false;
                //end if
                i = i + 1;
            }//end while

            return isEqual;
        }//end else
    }//end equals


    public String toString()
    {
        return "Rule #" + number + "\n"+
                "CONDITION: " + conditions + "\n"+
                "ACTION: " + commitments + "\n"+
                "Priority: " + priority + "\n";
    }//end toString

}//end class Rule

