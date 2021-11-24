/* eslint-disable react/jsx-props-no-spreading */
import React  from "react";
import styled from '@emotion/styled/macro';
import selectIconUrl from '../../../../public/image/화이팅도치.jpg';

const Select = [
    {name: 'RED', hex:'#ffb598'},
    {name: 'BLUE', hex:'#c2dbff'},
];

const ColorSelectorContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 500px;
  margin-top: 8px;
  padding: 10px;
  border: 1px solid salmon;
`;

const Label = styled.label`
  display: inline-block;
  width: 40px;
  height: 40px;
  border-radius: 20px;
  margin: 10px;
  padding: 10px;
  background-color: ${(props) => props.color};
`;

const RadioButton = styled.input`
  display: none;
  &:checked + ${Label} {
    background: center url(${selectIconUrl}) no-repeat ${(props) => props.color};
  }
`;

const SelectInfo: React.FC = () => {
    return(
        <>
        <ColorSelectorContainer>
        {Select.map((row)=> (
            <div key={row.name}>
                <RadioButton 
                    id={row.name}
                    type="radio"
                    name="select-info"
                    value={row.name}
                />
                <Label htmlFor={row.name} color={row.hex}>{row.name}</Label>
            </div>
        ))}
        </ColorSelectorContainer>
        </>
    );
};
export default SelectInfo;