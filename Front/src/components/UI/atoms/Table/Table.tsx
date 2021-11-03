import React from 'react';
import TableHeader from './TableHeader';
import TableBody from './TableBody';

const Table : React.FC = (props) => {
    const [columns, data, onSort, sortColumn] = useState(false);
    const {columns, data, onSort, sortColumn} = props;
    return (
        <table className = "table">
            <TableHeader columns = {columns} onSort = {onSort} sortColumn = {sortColumn} />
            <TableBody data = {data} columns = {columns} />
        </table>
    );
}

export dafault Table;