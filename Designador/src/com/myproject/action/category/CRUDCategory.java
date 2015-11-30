package com.myproject.action.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Category;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDCategory  extends ActionSupport implements ServletContextAware{

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idCategory;
	private String categoryName;
	private int categoryGender;
	private List<?> categories;
	private Category category;

	private GenericService service;

    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditCategory")){
			
			if (idCategory != null && !idCategory.equals("")){
				eqRestrictions.put("idCategory", new FieldCondition(idCategory));
				category = (Category) service.GetUniqueModelData(Category.class, eqRestrictions);
				if(category != null){
					setIdCategory(category.getIdCategory());
					setCategoryName(category.getCategoryName());
					setCategoryGender(category.getCategoryGender());
				}
			}
			
			return NONE;
			
		}else{
			categories = service.GetModelDataList(Category.class, eqRestrictions, "categoryName", true);
			return SUCCESS;
		}
	}

	public String addEditCategory(){
		
		if(categoryGender < Category.MALE || categoryGender > Category.MIXED)
			categoryGender = Category.MALE;
		
		/*Editing an existing Category*/
		if(idCategory != null && !idCategory.equals("")){
			eqRestrictions.put("idCategory", new FieldCondition(idCategory));
			category = (Category) service.GetUniqueModelData(Category.class, eqRestrictions);			
			
			if(category != null){
				
				if(!new Category(categoryName.trim(),categoryGender).equals(category)){
					if (categoryAlreadyExists(new Category(categoryName.trim(),categoryGender))){
						addActionError("Esta categoría ya existe. Por favor, añade otra.");
						return INPUT;
					}
					else{
						category.setCategoryGender(categoryGender);
						category.setCategoryName(categoryName.trim().substring(0,1).toUpperCase() + categoryName.trim().substring(1));
						service.SaveOrUpdateModelData(category);
					}
				}
		
				addActionMessage("Se ha actualizado el nombre de la categoría con exito.");
				return SUCCESS;
	
			}
		}
		
		/*Adding a new Category*/
		
		if(categoryAlreadyExists(new Category(categoryName.trim(),categoryGender))){
			addActionError("Esta categoría ya existe. Por favor, añade otra.");
			return INPUT;
		}
		else{
			
			category = new Category(categoryName.trim().substring(0,1).toUpperCase() + categoryName.trim().substring(1), categoryGender);
			service.SaveOrUpdateModelData(category);
			addActionMessage("Se ha sido añadido una nueva categoría con exito.");
			return SUCCESS;
		}
	}
	
	@SkipValidation
	public String deleteCategory(){
		
		if (idCategory == null || idCategory.equals("")){
			
			addActionError("Por favor, introduce el id de la categoría que quieres eliminar.");
			categories = service.GetModelDataList(Category.class, eqRestrictions, "categoryName", true);
			context.setAttribute("categories", categories);
			return INPUT;
		}
		else{
			eqRestrictions.put("idCategory", new FieldCondition(idCategory));
			category = (Category) service.GetUniqueModelData(Category.class, eqRestrictions);			
			
			//(System.out.println("hello " + idCategory);
			//System.out.println("hi " + category);

			if(category != null){
				service.DeleteModelData(category);
				addActionMessage("La categoría ha sido eliminada con exito.");
				return SUCCESS;

			}
			else{
				addActionError("La categoría que quieres eliminar no existe o ya se ha eliminado.");
				eqRestrictions.clear();
				categories = service.GetModelDataList(Category.class, eqRestrictions, "categoryName", true);
				context.setAttribute("categories", categories);
				return INPUT;
			}

		}
	}
	
	public Boolean categoryAlreadyExists(Category category){
		
		eqRestrictions.clear();
		eqRestrictions.put("categoryName", new FieldCondition(category.getCategoryName()));
		eqRestrictions.put("categoryGender", new FieldCondition(category.getCategoryGender()));

		return (Category) service.GetUniqueModelData(Category.class, eqRestrictions) != null;
		
	}
	
	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryGender() {
		return categoryGender;
	}

	public void setCategoryGender(int categoryGender) {
		this.categoryGender = categoryGender;
	}

	public List<?> getCategories() {
		return categories;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
