package personal.liyitong.search.entity;

import java.util.ArrayList;
import java.util.List;

public class Collection {
	
	private String id;

	private String name;

	private String collection;
	
	private String sqlStatement;
	
	private String subjectId;
	
	private String parentId;
	
	private String tableId;
	
	private String remark;

	private List<Collection> children = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Collection> getChildren() {
		return children;
	}

	public void setChildren(List<Collection> children) {
		this.children = children;
	}

	public static void buildTree(List<Collection> nodes, Collection root) {
		List<Collection> children = findChildrenById(root.getId(), nodes);
		root.setChildren(children);
		for (Collection child: children) {
			buildTree(nodes, child);
		}
	}

	public static List<Collection> findChildrenById(String id, List<Collection> nodes) {
		List<Collection> children = new ArrayList<>();
		for (Collection node: nodes) {
			if (id.equals(node.getParentId())) {
				children.add(node);
			}
		}
		return children;
	}

	public static Collection findNodeById(List<Collection> collections, String nodeId) {
		Collection node = null;
		for (Collection c: collections) {
			if (nodeId.equals(c.getId())) {
				node = c;
				break;
			}
		}
		return node;
	}

	public static List<Collection> getRootNodes(List<Collection> collections) {
		List<Collection> roots = new ArrayList<>();
		for (Collection c: collections) {
			if (c.getParentId() == null) {
				roots.add(c);
			}
		}
		return roots;
	}
}
