import React from "react";
import styled from "@emotion/styled";
import ProfileTitle from "../../components/molecules/Profile/ProfileTitle";
import ProfileContent from "../../components/molecules/Profile/ProfileContent";

const ProfilePage: React.FC = () => {
	return (
		<Wrapper>
			<ProfileTitle />
			<ProfileContent />
		</Wrapper>
	);
};

export default ProfilePage;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	min-height: 900px;
	background-color: #ffffff;

	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	position: relative;
`;
