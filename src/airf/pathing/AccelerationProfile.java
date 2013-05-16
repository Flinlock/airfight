package airf.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AccelerationProfile
{
    List<SectionData> sections;

    public AccelerationProfile()
    {
        sections = new ArrayList<>();
        sections.add(new SectionData(0, 0));
    }

    /**
     * Sets the acceleration to acc for any p
     * between pStart and the start of the next
     * section or 1.0, whichever is first.
     * 
     * @param pStart Start of this section.
     * @param acc Acceleration in this section.
     */
    public void addDivider(float pStart, float acc)
    {   
        Iterator<SectionData> it = sections.iterator();
        while(it.hasNext())
        {
            if(it.next().pStart == pStart)
                it.remove();
        }
        sections.add(new SectionData(pStart, acc));
        Collections.sort(sections);
    }
    
    public float getAcceleration(float p)
    {
        int i = findSectionIndexContaining(p);
        SectionData s = sections.get(i);
        return s.acc;
    }

    public float getEndP(float p)
    {
        int i = findSectionIndexContaining(p);

        SectionData s;
        if(i == sections.size()-1)
            return 1.0f;
        else
            s = sections.get(i+1);
        
        return s.pStart;
    }
    
    private int findSectionIndexContaining(float p)
    {
        if(p < 0 || p > 1)
            throw new IllegalArgumentException();
        
        if(p == 1.0)
            return sections.size()-1;
            
        for(int i = sections.size()-1; i >= 0; i--)
        {
            SectionData s = sections.get(i);
            if(s.pStart <= p)
                return i;
        }
        
        return -1;
    }
    
    private class SectionData implements Comparable<SectionData>
    {
        float pStart;
        float acc;
        
        public SectionData()
        {
            pStart = 0;
            acc = 0;
        }
        
        public SectionData(float pStart, float acc)
        {
            this.pStart = pStart;
            this.acc = acc;
        }

        @Override
        public int compareTo(SectionData d)
        {
            if(pStart < d.pStart)
                return -1;
            if(pStart > d.pStart)
                return 1;
            
            return 0;
        }
    }

}
