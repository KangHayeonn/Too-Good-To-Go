import React, { Component } from 'react';
 
class TableHeader extends Component {
 
    raiseSort = sort => {
        let sortColumn = { ...this.props.sortColumn };
 
//선택된 분류가 현재 분류와 같으면 내림차 순이면 오름차 순으로 오름차 순이면 내림차순으로 바꿔준다.
        if(sortColumn.path===sort)  sortColumn = {path:sort, order: sortColumn.order==="asc" ? "desc" : "asc"}
 
        //선택 분류가 현재 분류와 같지 않으면 선택 분류로 바꾸고 무조건 오름차 순으로 바꾸어준다.
 else {sortColumn= {path:sort, order:"asc"}} 
        this.props.onSort(sortColumn);
      };
 
       addSortIcon = column => {
          const {sortColumn} = this.props;
 
//선택되지 않은 분류들은 아이콘이 보이지않음
        if(sortColumn.path!==column.path) return null;
 
//현재 분류가 오름차순으로 되어있으면
        if(sortColumn.order==="asc") return <i className="fa fa-sort-asc"></i>
 
 //현재 분류가 내림차순이면
        if(sortColumn.order==="desc") return <i className="fa fa-sort-desc"></i>
 
      }
 
    render() {
        
        
        return ( 
            <thead>
                <tr>
                    {this.props.columns.map(column => 
//table head들에 들어갈 데이터들을 넣어주고 현재 분류의 상태에따라서 오름차순 내림차순 아이콘을 넣어준다.
                        <th key={column.path||column.key} onClick={() => this.raiseSort(column.path||column.key)}>{column.name}{this.addSortIcon(column)}</th>
                    )}
                    
                </tr>
            </thead>
         );
    }
}
 
export default TableHeader;
