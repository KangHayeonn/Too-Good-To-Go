import React from "react";
import styled from "@emotion/styled";
import MainMenuButton from "../../molecules/Main/MainMenuButton";

const MainMenuWrap = styled.ul`
	display: flex;
	width: 100%;
	flex-wrap: wrap;
`;

const MainMenu: React.FC = () => {
	return (
		<MainMenuWrap>
			<MainMenuButton
				to="전체보기"
				menuName="전체보기"
				image="https://cdn-icons-png.flaticon.com/512/1405/1405021.png"
			/>
			<MainMenuButton
				to="한식"
				menuName="한식"
				image="https://cdn.crowdpic.net/list-thumb/thumb_l_C60A826F9C278042362A1CCB1CA45476.png"
			/>
			<MainMenuButton
				to="분식"
				menuName="분식"
				image="https://post-phinf.pstatic.net/MjAxNzA3MTlfOTIg/MDAxNTAwNDUwMjU5NTE5.ymoJcEPiQFVMDJoYNbP8hUBJ6xpUXTSs4JGbu6qClHkg.H0YympXn2JPCfkuViD5-uJrg2g1F1fC1ZBynSIYIyYEg.PNG/hero_uMT8f_2015_10_21_18_54_41.png?type=w1200"
			/>
			<MainMenuButton
				to="야식"
				menuName="야식"
				image="https://cdn.crowdpic.net/detail-thumb/thumb_d_AABEB2798AD52657E9A550D55D60E0BC.png"
			/>
			<MainMenuButton
				to="양식"
				menuName="양식"
				image="https://mblogthumb-phinf.pstatic.net/20140805_155/ballvic123_1407218490159PdAhN_PNG/18.png?type=w2"
			/>
			<MainMenuButton
				to="일식"
				menuName="일식"
				image="https://t1.daumcdn.net/cfile/blog/9916CE4B5B894C7C2B"
			/>
			<MainMenuButton
				to="중식"
				menuName="중식"
				image="https://cdn.crowdpic.net/list-thumb/thumb_l_0C6C0DF34CA0CB0357A704E12CAF9FDA.png"
			/>
			<MainMenuButton
				to="패스트푸드"
				menuName="패스트푸드"
				image="https://t1.daumcdn.net/cfile/blog/994C3C405B88F55D1D"
			/>
			<MainMenuButton
				to="찜탕"
				menuName="찜 · 탕"
				image="https://cdn.crowdpic.net/detail-thumb/thumb_d_1149D64DA400F99337C177386B220421.png"
			/>
			<MainMenuButton
				to="디저트"
				menuName="디저트"
				image="https://cdn.crowdpic.net/list-thumb/thumb_l_F4D57BAA90BDB1A658BF1268CACE255A.png"
			/>
		</MainMenuWrap>
	);
};

export default MainMenu;
