interface CollapsedSidebarProps {
    toggleSidebar: () => void;
    isCollapsed: boolean;
  }
  
  export const CollapsedSidebar = ({
    toggleSidebar,
    isCollapsed,
  }: CollapsedSidebarProps) => {
    return (
      <div className="flex flex-col items-center">
        <button
          onClick={toggleSidebar}
          className="mt-2 p-2 rounded hover:bg-gray-200"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            {/* Стрелка для сворачивания (когда панель открыта) */}
            {!isCollapsed && (
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M15 19l-7-7 7-7"
              />
            )}
            {/* Стрелка для разворачивания (когда панель свернута) */}
            {isCollapsed && (
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M9 5l7 7-7 7"
              />
            )}
          </svg>
        </button>
      </div>
    );
  };