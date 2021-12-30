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

const ShopNameText = styled.p`
	color: #fff;
	font-size: 19px;
	font-weight: 600;
	margin-right: 20px;
`;

const ManagerMenu: React.FC = () => {
	return (
		<HeaderMenu>
			<ManagerMenuAtom menuName="주문접수" />
			<ShopNameText>가게 이름</ShopNameText>
		</HeaderMenu>
	);
};

export default ManagerMenu;
