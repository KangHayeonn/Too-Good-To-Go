import React from "react";
import styled from "@emotion/styled";
import ManagerMenuAtom from "../../atoms/MenuButton/ManagerMenuAtom";

const HeaderMenu = styled.ul`
	display: flex;
	width: 100%;
	height: auto;
	background-color: #363636;
	justify-content: space-between;
	align-items: center;
`;

const ManagerMenu: React.FC = () => {
	return (
		<HeaderMenu>
			<ManagerMenuAtom menuName="주문접수" />
		</HeaderMenu>
	);
};

export default ManagerMenu;
