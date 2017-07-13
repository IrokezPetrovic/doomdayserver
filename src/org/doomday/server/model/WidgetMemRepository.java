package org.doomday.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
		
		Optional<Widget> saved = widgets.stream()
		.filter(widget->widget.get_id().equals(w.get_id()))
		.findFirst();
		try{
		return saved.get().merge(w);
		} catch(NoSuchElementException e){
			widgets.add(w);
			return w;
		}
		/*
		if (saved.get() != null){
			return saved.get().merge(w);
		} else {			
			widgets.add(w);
			return w;
		}
		*/
		
	}

	@Override
	public Collection<Widget> getWidgets(String get_id) {		
		return widgets.stream()
		.filter(w->w.getDashboards().contains(get_id))
		.collect(Collectors.toSet());
		
	}

	@Override
	public Collection<Widget> getWidgets() {
		return widgets;		
	}

	@Override
	public boolean remove(String id) {
		System.out.println("Removing widget with id="+id);
		Optional<Widget> findFirst = widgets.stream()
		.filter(w->w.get_id().equals(id))
		.findFirst();
		return widgets.remove(findFirst.get());
	}

}
