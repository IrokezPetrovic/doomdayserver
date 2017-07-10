package org.doomday.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.beans.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WidgetMemRepository implements IWidgetRepository{

	private Set<Widget> widgets = new HashSet<>();
		
	@Autowired
	ObjectMapper mapper;
	
	@PostConstruct
	public void init(){
		try{
			FileReader fr = new FileReader("/tmp/widgets.conf");
			BufferedReader br = new BufferedReader(fr);		
			String line = "";
			while((line=br.readLine())!=null){
				Widget w = mapper.readValue(line, Widget.class);
				widgets.add(w);
			}
			br.close();
		} catch (IOException e){
			
		}
	}
	
	@PreDestroy
	public void destroy() {
		try{
			FileWriter fw = new FileWriter("/tmp/widgets.conf");
			widgets
			.forEach(w->{
				try {
					fw.write(mapper.writeValueAsString(w)+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});		
			fw.flush();
			fw.close();
		} catch(IOException e){
			
		}
	}
	
			

	@Override
	public Widget save(Widget w) {
		if (w.get_id()==null){
			w.set_id(Integer.toHexString(w.hashCode()));			
		}
		System.out.println("Saved widget "+w.get_id()+" "+w.getConfig());
		widgets.add(w);
		return w;
	}

}
