import type { ReactNode } from "react";
import { Navbar } from "./navbar";
import { Sidebar } from "./sidebar";

interface AppShellProps {
  children: ReactNode;
}

export function AppShell({ children }: AppShellProps) {
  return (
    <div className="min-h-screen bg-[#07090d] text-white">
      {/* Fixed sidebar */}
      <Sidebar />

      {/* Main application area */}
      <div className="ml-64 min-h-screen">
        {/* Fixed topbar */}
        <div className="sticky top-0 z-40">
          <Navbar />
        </div>

        {/* Only this area scrolls */}
        <main className="min-h-[calc(100vh-5rem)]">
          {children}
        </main>
      </div>
    </div>
  );
}