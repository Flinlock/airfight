package airf.pathing;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AccelerationProfile implements Iterable<Point2D.Float>
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

    // TODO: Change this from Point2D.Float to something with meaningful field names
    @Override
    public Iterator<Point2D.Float> iterator() 
    {
        return new Iterator<Point2D.Float>()
        {
            int index = 0;

            @Override
            public boolean hasNext()
            {
                return sections.size() > index;
            }

            @Override
            public Point2D.Float next()
            {
                Point2D.Float ret = new Point2D.Float();
                SectionData sec = sections.get(index);
                
                if(index+1 == sections.size())
                {
                    ret.x = 1.0f;
                    ret.y = sec.acc;
                }
                else
                {
                    SectionData nSec = sections.get(index+1);
                    ret.y = sec.acc;
                    ret.x = nSec.pStart;
                }
                

                index++;                    
                return ret;
            }

            @Override
            public void remove()
            {                
            }
            
        };
    }

}
