import React from "react";
import styled from "@emotion/styled";
import MenuAtom from "../../atoms/MenuButton/MenuAtom";

const HeaderMenu = styled.div`
	display: flex;
	width: 100%;
	height: auto;
`;

const ShopHeaderMenu: React.FC = () => {
	return (
		<HeaderMenu>
			<MenuAtom to="1인분" menuName="1인분" />
			<MenuAtom to="치킨" menuName="치킨" />
			<MenuAtom to="피자양식" menuName="피자 · 양식" />
			<MenuAtom to="중국집" menuName="중국집" />
			<MenuAtom to="한식" menuName="한식" />
			<MenuAtom to="일식돈까스" menuName="일식 · 돈까스" />
			<MenuAtom to="족발보쌈" menuName="족발 · 보쌈" />
			<MenuAtom to="야식" menuName="야식" />
			<MenuAtom to="카페디저트" menuName="카페 · 디저트" />
		</HeaderMenu>
	);
};

export default ShopHeaderMenu;
