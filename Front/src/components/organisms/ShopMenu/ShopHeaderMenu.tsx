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
			<MenuAtom to="korean" menuName="1인분" />
			<MenuAtom to="korean" menuName="치킨" />
			<MenuAtom to="korean" menuName="피자 · 양식" />
			<MenuAtom to="korean" menuName="중국집" />
			<MenuAtom to="korean" menuName="한식" />
			<MenuAtom to="korean" menuName="일식 · 돈까스" />
			<MenuAtom to="korean" menuName="족발 · 보쌈" />
			<MenuAtom to="korean" menuName="야식" />
			<MenuAtom to="korean" menuName="카페 · 디저트" />
		</HeaderMenu>
	);
};

export default ShopHeaderMenu;
