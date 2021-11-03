import React, { Component } from 'react';
import _ from 'lodash';
class TableBody extends Component {
 
//table data 넣어주는 메소드
   renderCell = (data, column) => {
 
   //column의 content property이면(좋아요랑 삭제 버튼)
   if(column.content) return column.content(data);
 
//아니면 제목, 장르 등등에 맞는 데이터를 반환함
   return _.get(data, column.path);
   };
 
 
 
    render() {
        const {data, columns} = this.props;
        return ( 
            <tbody>
 //영화 갯수에 맞게 끔 table row를 생성 그다음 분류에 맞게 끔 table data들을 넣어줌
                {data.map(data => <tr key={data._id}>{columns.map(column => <td key={data._id+(column.path || column.key)}>{this.renderCell(data, column)}</td>)}
                </tr> )}
                
            </tbody>
         );
    }
}
 
export default TableBody;
