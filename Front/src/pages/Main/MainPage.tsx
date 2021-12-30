import React from "react";
import MainMenu from "../../components/organisms/Main/MainMenu";
import MainSwiper from "../../components/organisms/Main/MainSwiper";
import PageTemplate from "../../components/templates/PageTemplate";

const MainPage: React.FC = () => {
	return (
		<PageTemplate
			header={<MainSwiper />}
			isHeader
			isSection={false}
			isFooter={false}
		>
			<MainMenu />
		</PageTemplate>
	);
};

export default MainPage;
